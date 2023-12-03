package com.task.library.service;

import java.util.List;
import java.util.Optional;

import com.task.library.dto.list.ListDTO;
import com.task.library.exception.AppException;

public interface ListService {

	Optional<ListDTO> findByListId(String userId, Long listId) throws AppException;
	
	Optional<List<ListDTO>> listAllListsOfUser(String userId) throws AppException;
	
	Long createList(ListDTO list) throws Exception;
	
	Long removeList(String userId, Long listId) throws AppException;
	
	Optional<ListDTO> updateList(ListDTO listDTO, boolean checkExist) throws AppException;
	
	Optional<List<ListDTO>> getListsOfTask(String userId, Long taskId, boolean safe) throws AppException;
}
