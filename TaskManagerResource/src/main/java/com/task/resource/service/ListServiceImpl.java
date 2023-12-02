package com.task.resource.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.task.library.dto.list.ListDTO;
import com.task.library.exception.AlreadyExistsException;
import com.task.library.exception.AppException;
import com.task.library.kafka.KafkaAppEvent;
import com.task.library.kafka.KafkaListMessage;
import com.task.library.kafka.KafkaTopics;
import com.task.library.service.ListService;
import com.task.library.service.TaskListService;
import com.task.library.service.TaskService;
import com.task.resource.annotation.TimeLogger;
import com.task.resource.model.List;
import com.task.resource.model.TaskList;
import com.task.resource.repository.ListRepository;
import com.task.resource.repository.TaskListRepository;

@Service
public class ListServiceImpl implements ListService {

	private final static String DEFAULT_COLOR = "#A8DF8E";
	private final static String DEFAULT_USER = "-1";
	private static final Logger LOGGER = LoggerFactory.getLogger(ListServiceImpl.class);
	
	@Autowired
	private ListRepository listRepository;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskListRepository taskListRepository;

	@Autowired
	private TaskListService taskListService;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	@Override
	@TimeLogger
	public Optional<ListDTO> findByListId(String userId, Long listId) {
		Optional<List> list = listRepository.findByListIdAndUserId(listId, userId);
		
		if(list.isEmpty()) {
			/**
			 * This is for handling get request for default Personal, Work lists.
			 */
			list = listRepository.findByListIdAndUserId(listId, DEFAULT_USER);

			if(list.isEmpty()) return Optional.empty(); 
		}
		Optional<java.util.List<TaskList>> taskLists = taskListRepository.findByListAndUserId(list.get(), userId);

		ListDTO listDTO = list.get().toListDTO();
		if(taskLists.isPresent()) {
			listDTO.setTaskCount(taskLists.get().size());
		}
		return Optional.of(listDTO);
	}

	@Override
	@TimeLogger
	public Optional<java.util.List<ListDTO>> listAllListsOfUser(String userId) {
		
		Optional<java.util.List<List>> lists = listRepository.findByUserId(userId);
		
		if(lists.isEmpty()) {
			lists = Optional.of(new ArrayList<>());
		}
		fillDefaultLists(lists.get());

		java.util.List<ListDTO> listDTOs = lists.get().stream()
												.map(list -> {
													ListDTO listDTO = list.toListDTO();
													Optional<java.util.List<TaskList>> taskLists = 
														taskListRepository.findByListAndUserId(list, userId);
													if(taskLists.isPresent()) {
														listDTO.setTaskCount(taskLists.get().size());
													}
													return listDTO;
												})
												.toList();
		return Optional.of(listDTOs);
	}

	@Override
	@TimeLogger
	public Long createList(ListDTO listDTO) throws Exception {
		sanitizeInput(listDTO);
		
		if(listRepository.findByUserIdAndListName(listDTO.getUserId(), listDTO.getListName()).isPresent()) {
			throw new AlreadyExistsException("List name already exists", 400);
		}
		List list =  List.toList(listDTO);
		List createdList = listRepository.save(list);
		
		if(createdList == null) {
			throw new Exception("Error while creating list");
		}
		LOGGER.info("Created list {}", createdList.getListId());

		KafkaListMessage kafkaListMessage = new KafkaListMessage(KafkaAppEvent.CREATE, createdList.toListDTO());
		kafkaTemplate.send(KafkaTopics.LIST, kafkaListMessage);
		
		return createdList.getListId();
	}

	@Override
	@TimeLogger
	public Long removeList(String userId, Long listId) {
		taskListService.deleteAllRelationOfList(userId, listId);
		
		java.util.List<List> deletedLists = listRepository.deleteByUserIdAndListId(userId, listId);
		
		if(deletedLists != null && deletedLists.size() > 0) {
			LOGGER.info("Removed list {}", deletedLists.get(0).getListId());

			KafkaListMessage kafkaListMessage = new KafkaListMessage(KafkaAppEvent.DELETE, deletedLists.get(0).toListDTO());
			kafkaTemplate.send(KafkaTopics.LIST, kafkaListMessage);
			return deletedLists.get(0).getListId();
		}
		return null;
	}

	@Override
	@TimeLogger
	public Optional<ListDTO> updateList(ListDTO listDTO) {
		Optional<List> existingList = listRepository.findByListIdAndUserId(listDTO.getListId(), listDTO.getUserId());
		
		if(existingList.isEmpty()) {
			throw new IllegalArgumentException("No list exists with listId => " + listDTO.getListId());
		}
		List newList = List.toList(listDTO);
		checkAndUpdateList(existingList.get(), newList);
		
		List updatedList = listRepository.save(newList);

		LOGGER.info("Updated list {}", updatedList.getListId());

		KafkaListMessage kafkaListMessage = new KafkaListMessage(KafkaAppEvent.UPDATE, updatedList.toListDTO());
		kafkaTemplate.send(KafkaTopics.LIST, kafkaListMessage);
		
		return Optional.of(updatedList.toListDTO());
	}

	@Override
	@TimeLogger
	public Optional<java.util.List<ListDTO>> getListsOfTask(String userId, Long taskId, boolean safe) throws AppException {
		if(!taskService.isTaskExists(userId, taskId)) {
			if(safe) {
				return Optional.empty();
			}
			throw new IllegalArgumentException("No task found with the given taskId => " + taskId);
		}
		
		Optional<java.util.List<TaskList>> taskList = taskListRepository.findByTaskTaskIdAndUserId(taskId, userId);
		if(taskList.isEmpty()) {
			Optional.empty();
		}
		java.util.List<ListDTO> lists = taskList.get()
												.stream()
												.map(tList -> tList.getList().toListDTO())
												.toList();
		return Optional.of(lists);
	}

	private void sanitizeInput(ListDTO listDTO) {
		if(listDTO.getListName() != null) {
			listDTO.setListName(listDTO.getListName().trim());
			
			if(listDTO.getListName().length() < 3) {
				throw new IllegalArgumentException("List name should be greater than or equal to 3");
			}
			if(listDTO.getListName().length() > 15) {
				throw new IllegalArgumentException("List name length should be lesser than or equal to 15");
			}
			listDTO.setListName(HtmlUtils.htmlEscape(listDTO.getListName()));
		}
		if(listDTO.getListColor() == null) {
			listDTO.setListColor(DEFAULT_COLOR);
		}
		else {
			listDTO.setListColor(listDTO.getListColor().trim());
		}
	}
	
	private void checkAndUpdateList(List existingList, List newList) {
		if(newList.getListColor() == null) {
			//Not checking for color check in existing list
			//because list has a default value when inserting into DB.
			newList.setListColor(existingList.getListColor());
		}
		if(newList.getListName() == null) {
			newList.setListName(existingList.getListName());
		}
	}

	private void fillDefaultLists(java.util.List<List> lists) {
		Optional<java.util.List<List>> defaultLists =  listRepository.findByUserId(DEFAULT_USER);

		lists.addAll(0, defaultLists.get());
	}
}
