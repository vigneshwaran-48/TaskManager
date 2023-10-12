package com.task.library.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.library.dto.ListDTO;
import com.task.library.dto.list.ListBodyListResponse;
import com.task.library.dto.list.ListBodyResponse;
import com.task.library.dto.list.ListCreationResponse;
import com.task.library.dto.list.ListDeletionResponse;
import com.task.library.exception.AppException;
import com.task.library.service.ListService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/list")
@CrossOrigin(value = "*")
public class ListController {

	private final static String BASE_PATH = "/api/v1/list";
	
	@Autowired
	private ListService listService;
	
	@PostMapping
	public ResponseEntity<?> createList(@Valid @RequestBody ListDTO listDTO) throws Exception {
		
		//Need to remove this hardcoded value after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		listDTO.setUserId(userId);
		Long listId = listService.createList(listDTO);
		
		ListCreationResponse response = new ListCreationResponse();
		response.setMessage("success");
		response.setStatus(HttpStatus.CREATED.value());
		response.setListId(listId);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + listId);

		Thread.sleep(2000);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{listId}")
	public ResponseEntity<?> getListById(@PathVariable Long listId) {
		
		//Need to remove this hardcoded value after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		
		ListDTO listDTO = listService.findByListId(userId, listId).orElse(null);
		if(listDTO != null) {
			fillWithLinks(listDTO);
		}
		ListBodyResponse response = new ListBodyResponse();
		response.setMessage("success");
		response.setStatus(listDTO != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setList(listDTO);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + listId);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllListsOfUser() {
		
		//Need to remove this hardcoded value after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		
		List<ListDTO> lists = listService.listAllListsOfUser(userId).orElse(null);
		
		if(lists != null) {
			lists.forEach(this::fillWithLinks);
		}
		
		ListBodyListResponse response = new ListBodyListResponse();
		response.setMessage("success");
		response.setStatus(lists != null && !lists.isEmpty() 
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setLists(lists);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/list");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("{listId}")
	public ResponseEntity<?> deleteByListId(@PathVariable Long listId) {
		
		//Need to remove this hardcoded value after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		
		Long removedList = listService.removeList(userId, listId);
		ListDeletionResponse response = new ListDeletionResponse();
		response.setMessage("success");
		response.setStatus(removedList != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setDeletedLists(removedList != null ? List.of(removedList) : List.of());
		response.setTime(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/bytask/{taskId}")
	public ResponseEntity<?> getListsByTaskId(@PathVariable Long taskId) throws AppException {
		
		//Need to remove this hardcoded value after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		
		List<ListDTO> lists = listService.getListsOfTask(userId, taskId).orElse(null);
		
		if(lists != null) {
			lists.forEach(this::fillWithLinks);
		}
		ListBodyListResponse response = new ListBodyListResponse();
		response.setMessage("success");
		response.setStatus(lists != null && !lists.isEmpty() 
				? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setLists(lists);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/list");
		
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("{listId}")
	public ResponseEntity<?> patchUpdateList(@PathVariable Long listId, @RequestBody ListDTO listDTO) {
		
		//Need to remove this hardcoded value after spring sevurity enabled
		//and get the user id from principal.
		String userId = "12";
		listDTO.setListId(listId);
		listDTO.setUserId(userId);
		
		ListDTO updatedList = listService.updateList(listDTO).orElse(null);
		if(updatedList != null) {
			fillWithLinks(updatedList);
		}
		ListBodyResponse response = new ListBodyResponse();
		response.setMessage("success");
		response.setStatus(updatedList != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setList(updatedList);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + listId);
		
		return ResponseEntity.ok(response);
	}
	
	private void fillWithLinks(ListDTO listDTO) {
		Map<String, Object> links = new HashMap<>();
		links.put("self", BASE_PATH + "/" + listDTO.getListId());
		links.put("update", BASE_PATH + "/" + listDTO.getListId());
		links.put("delete", BASE_PATH + "/" + listDTO.getListId());
		links.put("all", BASE_PATH);
		
		listDTO.setLinks(links);
	}
	
}
