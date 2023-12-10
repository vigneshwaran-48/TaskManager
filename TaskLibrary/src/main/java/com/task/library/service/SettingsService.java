package com.task.library.service;

import java.util.Optional;

import com.task.library.dto.setting.SettingsDTO;
import com.task.library.exception.AppException;

public interface SettingsService {

    Optional<SettingsDTO> getSettings(String userId) throws AppException;

    void addSettingsToUser(String userId) throws AppException;

    Optional<SettingsDTO> updateSettings(String userId, SettingsDTO settingsDTO) throws AppException;
    
}