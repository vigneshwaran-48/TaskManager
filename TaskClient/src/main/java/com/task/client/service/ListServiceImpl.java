package com.task.client.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.task.library.dto.ListDTO;
import com.task.library.service.ListService;

@Service
public class ListServiceImpl implements ListService {

    @Override
    public Optional<ListDTO> findByListId(String userId, Long listId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByListId'");
    }

    @Override
    public Optional<List<ListDTO>> listAllListsOfUser(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listAllListsOfUser'");
    }

    @Override
    public Long createList(ListDTO list) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createList'");
    }

    @Override
    public Long removeList(String userId, Long listId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeList'");
    }

    @Override
    public Optional<ListDTO> updateList(ListDTO listDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateList'");
    }

    @Override
    public Optional<List<ListDTO>> getListsOfTask(String userId, Long taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListsOfTask'");
    }
    
}
