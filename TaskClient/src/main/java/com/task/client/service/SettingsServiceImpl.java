package com.task.client.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.task.library.dto.setting.SettingsDataResponse;
import com.task.library.dto.NoDataResponse;
import com.task.library.dto.setting.SettingsDTO;
import com.task.library.exception.AppException;
import com.task.library.service.SettingsService;

import reactor.core.publisher.Mono;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private WebClient webClient;

    private final static String BASE_PATH = "/api/v1/settings";

    @Override
    public Optional<SettingsDTO> getSettings(String userId) throws AppException {
        SettingsDataResponse response = webClient.get()
                                        .uri(BASE_PATH)
                                        .retrieve()
                                        .bodyToMono(SettingsDataResponse.class)
                                        .block();
        if(response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getData());
    }

    @Override
    public void addSettingsToUser(String userId) throws AppException {
        NoDataResponse response = webClient.post()
                                            .uri(BASE_PATH)
                                            .retrieve()
                                            .bodyToMono(NoDataResponse.class)
                                            .block();
        if(response.getStatus() != HttpStatus.CREATED.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
    }

    @Override
    public Optional<SettingsDTO> updateSettings(String userId, SettingsDTO settingsDTO) throws AppException {
        SettingsDataResponse response = webClient.put()
                                        .uri(BASE_PATH)
                                        .body(Mono.just(settingsDTO), SettingsDTO.class)
                                        .retrieve()
                                        .bodyToMono(SettingsDataResponse.class)
                                        .block();
        if(response.getStatus() != HttpStatus.OK.value()) {
            throw new AppException(response.getMessage(), response.getStatus());
        }
        return Optional.of(response.getData());
    }
    
}
