package com.task.library.service;

import java.util.List;
import java.util.Optional;

import com.task.library.dto.list.ListDTO;
import com.task.library.exception.AppException;

public interface ListService {

	Optional<ListDTO> findByListId(String userId, String listId) throws AppException;
	
	Optional<List<ListDTO>> listAllListsOfUser(String userId) throws AppException;
	
	String createList(ListDTO list) throws Exception;
	
	String removeList(String userId, String listId) throws AppException;
	
	Optional<ListDTO> updateList(ListDTO listDTO) throws AppException;
	
	Optional<List<ListDTO>> getListsOfTask(String userId, String taskId, boolean safe) throws AppException;
}
