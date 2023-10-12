package com.task.library.service;

import java.util.List;
import java.util.Optional;

import com.task.library.dto.ListDTO;
import com.task.library.exception.AppException;

public interface ListService {

	Optional<ListDTO> findByListId(String userId, Long listId);
	
	Optional<List<ListDTO>> listAllListsOfUser(String userId);
	
	Long createList(ListDTO list) throws Exception;
	
	Long removeList(String userId, Long listId);
	
	Optional<ListDTO> updateList(ListDTO listDTO);
	
	Optional<List<ListDTO>> getListsOfTask(String userId, Long taskId) throws AppException;
}
