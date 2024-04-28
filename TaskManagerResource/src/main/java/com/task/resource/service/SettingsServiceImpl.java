package com.task.resource.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.task.library.dto.setting.SettingsDTO;
import com.task.library.exception.AppException;
import com.task.library.service.SettingsService;
import com.task.resource.model.Settings;
import com.task.resource.repository.SettingsRepository;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsServiceImpl.class);

    @Override
    public Optional<SettingsDTO> getSettings(String userId) throws AppException {
        Optional<Settings> settings = settingsRepository.findByUserId(userId);
        if (settings.isEmpty()) {
            LOGGER.error("Settings is not added for user {}", userId);
            throw new AppException("Settings is not added for user", HttpStatus.BAD_REQUEST.value());
        }
        return Optional.of(settings.get().toSettingsDTO());
    }

    @Override
    public void addSettingsToUser(String userId) throws AppException {
        Settings settings = new Settings();
        settings.setUserId(userId);

        try {
            settingsRepository.save(settings);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("Error while adding settings for user {}", userId);
            throw new AppException("Error while adding settings for user", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public Optional<SettingsDTO> updateSettings(String userId, SettingsDTO settingsDTO) throws AppException {
        settingsDTO.setUserId(userId);
        try {
            return Optional.of(settingsRepository.save(Settings.toSettings(settingsDTO)).toSettingsDTO());
        } catch (Exception e) {
            LOGGER.error("Error while updating settings for user {}", userId);
            throw new AppException("Error while updating settings for user", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
