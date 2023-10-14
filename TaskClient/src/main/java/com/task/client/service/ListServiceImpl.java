package com.task.client.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.task.library.dto.ListDTO;
import com.task.library.dto.list.ListBodyListResponse;
import com.task.library.dto.list.ListBodyResponse;
import com.task.library.dto.list.ListCreationResponse;
import com.task.library.dto.list.ListDeletionResponse;
import com.task.library.exception.AppException;
import com.task.library.service.ListService;

import reactor.core.publisher.Mono;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    private WebClient webClient;
  
    @Value("${app.resource.server.baseurl}")
    private String resourceServerBaseURL;
    
    private final static String BASE_URL = "/api/v1/list";    
    private final static String SLASH = "/";

    @Override
    public Optional<ListDTO> findByListId(String userId, Long listId) {
        
        ListBodyResponse response = webClient.get()
                                             .uri(resourceServerBaseURL + BASE_URL + SLASH + listId)
                                             .retrieve()
                                             .bodyToMono(ListBodyResponse.class)
                                             .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        else if (response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getList());
    }

    @Override
    public Optional<List<ListDTO>> listAllListsOfUser(String userId) {
        ListBodyListResponse response = webClient.get()
                                             .uri(resourceServerBaseURL + BASE_URL)
                                             .retrieve()
                                             .bodyToMono(ListBodyListResponse.class)
                                             .block();
        System.out.println(response);
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        else if (response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        System.out.println("List response => " + response.getLists());
        return Optional.of(response.getLists());
    }

    @Override
    public Long createList(ListDTO list) throws Exception {
        
        ListCreationResponse response = webClient.post()
                                                 .uri(resourceServerBaseURL + BASE_URL)
                                                 .body(Mono.just(list), ListDTO.class)
                                                 .retrieve()
                                                 .bodyToMono(ListCreationResponse.class)
                                                 .block();
        if(response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return response.getListId();
    }

    @Override
    public Long removeList(String userId, Long listId) {
        ListDeletionResponse response = webClient.delete()
                                                 .uri(resourceServerBaseURL + BASE_URL + SLASH + listId)
                                                 .retrieve()
                                                 .bodyToMono(ListDeletionResponse.class)
                                                 .block();
        if(response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return response.getDeletedLists().get(0);
    }

    @Override
    public Optional<ListDTO> updateList(ListDTO listDTO) {
        ListBodyResponse response = webClient.patch()
                                                 .uri(resourceServerBaseURL + BASE_URL + SLASH + listDTO.getListId())
                                                 .retrieve()
                                                 .bodyToMono(ListBodyResponse.class)
                                                 .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        else if (response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getList());
    }

    @Override
    public Optional<List<ListDTO>> getListsOfTask(String userId, Long taskId) {
        StringBuffer urlBuffer = new StringBuffer(resourceServerBaseURL);
        urlBuffer.append(BASE_URL).append(SLASH).append("bytask").append(SLASH).append(taskId);

        ListBodyListResponse response = webClient.patch()
                                                 .uri(urlBuffer.toString())
                                                 .retrieve()
                                                 .bodyToMono(ListBodyListResponse.class)
                                                 .block();
        if(response.getStatus() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        else if (response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getLists());
    }
    
}
