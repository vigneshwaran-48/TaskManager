package com.task.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.task.library.dto.ListDTO;
import com.task.library.exception.AlreadyExistsException;
import com.task.library.service.ListService;
import com.task.library.service.TaskService;
import com.task.model.List;
import com.task.model.TaskList;
import com.task.repository.ListRepository;
import com.task.repository.TaskListRepository;

@Service
public class ListServiceImpl implements ListService {

	private final static String DEFAULT_COLOR = "#A8DF8E";
	private final static String DEFAULT_USER = "-1";
	
	@Autowired
	private ListRepository listRepository;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskListRepository taskListRepository;
	
	@Override
	public Optional<ListDTO> findByListId(String userId, Long listId) {
		Optional<List> list = listRepository.findByListIdAndUserId(listId, userId);
		
		if(list.isEmpty()) {
			return Optional.empty(); 
		}
		Optional<java.util.List<TaskList>> taskLists = taskListRepository.findByList(list.get());

		ListDTO listDTO = list.get().toListDTO();
		if(taskLists.isPresent()) {
			listDTO.setTaskCount(taskLists.get().size());
		}
		return Optional.of(listDTO);
	}

	@Override
	public Optional<java.util.List<ListDTO>> listAllListsOfUser(String userId) {
		Optional<java.util.List<List>> lists = listRepository.findByUserId(userId);
		
		if(lists.isEmpty()) {
			return Optional.empty();
		}
		fillDefaultLists(lists.get());

		java.util.List<ListDTO> listDTOs = lists.get().stream()
												.map(list -> {
													ListDTO listDTO = list.toListDTO();
													Optional<java.util.List<TaskList>> taskLists = 
														taskListRepository.findByList(list);
													if(taskLists.isPresent()) {
														listDTO.setTaskCount(taskLists.get().size());
													}
													return listDTO;
												})
												.toList();
		return Optional.of(listDTOs);
	}

	@Override
	public Long createList(ListDTO listDTO) throws Exception {
		sanitizeInput(listDTO);
		
		if(listRepository.findByListName(listDTO.getListName()).isPresent()) {
			throw new AlreadyExistsException("List name already exists", 400);
		}
		List list =  List.toList(listDTO);
		List createdList = listRepository.save(list);
		
		if(createdList == null) {
			throw new Exception("Error while creating list");
		}
		
		return createdList.getListId();
	}

	@Override
	public Long removeList(String userId, Long listId) {
		java.util.List<List> deletedLists = listRepository.deleteByUserIdAndListId(userId, listId);
		
		if(deletedLists != null && deletedLists.size() > 0) {
			return deletedLists.get(0).getListId();
		}
		return null;
	}

	@Override
	public Optional<ListDTO> updateList(ListDTO listDTO) {
		Optional<List> existingList = listRepository.findByListIdAndUserId(listDTO.getListId(), listDTO.getUserId());
		
		if(existingList.isEmpty()) {
			throw new IllegalArgumentException("No list exists with listId => " + listDTO.getListId());
		}
		List newList = List.toList(listDTO);
		checkAndUpdateList(existingList.get(), newList);
		
		List updatedList = listRepository.save(newList);
		return Optional.of(updatedList.toListDTO());
	}

	@Override
	public Optional<java.util.List<ListDTO>> getListsOfTask(String userId, Long taskId) {
		if(!taskService.isTaskExists(userId, taskId)) {
			throw new IllegalArgumentException("No task found with the given taskId => " + taskId);
		}
		
		Optional<java.util.List<TaskList>> taskList = taskListRepository.findByTaskTaskId(taskId);
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
