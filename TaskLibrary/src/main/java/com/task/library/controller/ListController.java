package com.task.library.controller;

import java.security.Principal;
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
import com.task.library.util.AuthUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/list")
@CrossOrigin(value = "*")
public class ListController {

	private final static String BASE_PATH = "/api/v1/list";
	private final static String NOT_AUTHENTICATED = "Not Authenticated";
	
	@Autowired
	private ListService listService;
	
	@PostMapping
	public ResponseEntity<?> createList(@Valid @RequestBody ListDTO listDTO, Principal principal) throws Exception {
		
		StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		listDTO.setUserId(userId.toString());
		Long listId = listService.createList(listDTO);
		
		ListCreationResponse response = new ListCreationResponse();
		response.setMessage("success");
		response.setStatus(HttpStatus.CREATED.value());
		response.setListId(listId);
		response.setTime(LocalDateTime.now());
		response.setPath(BASE_PATH + "/" + listId);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{listId}")
	public ResponseEntity<?> getListById(@PathVariable Long listId, Principal principal) {
		
		StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		
		ListDTO listDTO = listService.findByListId(userId.toString(), listId).orElse(null);
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
	public ResponseEntity<?> getAllListsOfUser(Principal principal) {
		
		StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		
		List<ListDTO> lists = listService.listAllListsOfUser(userId.toString()).orElse(null);
		
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
	
	@DeleteMapping("{listId}")
	public ResponseEntity<?> deleteByListId(@PathVariable Long listId, Principal principal) {
		
		StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		
		Long removedList = listService.removeList(userId.toString(), listId);
		ListDeletionResponse response = new ListDeletionResponse();
		response.setMessage("success");
		response.setStatus(removedList != null ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value());
		response.setDeletedLists(removedList != null ? List.of(removedList) : List.of());
		response.setTime(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/bytask/{taskId}")
	public ResponseEntity<?> getListsByTaskId(@PathVariable Long taskId, Principal principal) throws AppException {
		
		StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		
		List<ListDTO> lists = listService.getListsOfTask(userId.toString(), taskId).orElse(null);
		
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
	public ResponseEntity<?> patchUpdateList(@PathVariable Long listId, 
											 @RequestBody ListDTO listDTO, Principal principal) {
		
		StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
		listDTO.setListId(listId);
		listDTO.setUserId(userId.toString());
		
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
