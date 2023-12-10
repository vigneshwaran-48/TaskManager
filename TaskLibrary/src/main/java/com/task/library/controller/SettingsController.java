package com.task.library.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.library.dto.setting.SettingsDataResponse;
import com.task.library.dto.NoDataResponse;
import com.task.library.dto.setting.SettingsDTO;
import com.task.library.exception.AppException;
import com.task.library.service.SettingsService;
import com.task.library.util.AuthUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/settings")
public class SettingsController {
    
    @Autowired
    private SettingsService settingsService;

    private final static String BASE_PATH = "/api/v1/settings";
	private final static String NOT_AUTHENTICATED = "Not Authenticated";
    private Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

    @GetMapping
    public ResponseEntity<?> getSettings(Principal principal) throws AppException {

        StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}

        Optional<SettingsDTO> settingsDTO = Optional.empty();

        try {
            settingsDTO = settingsService.getSettings(userId.toString());
        }
        catch(AppException e) {
            if(e.getStatus() == HttpStatus.BAD_REQUEST.value()) {
                LOGGER.info("Creating setting for user");

                settingsService.addSettingsToUser(userId.toString());
                settingsDTO = settingsService.getSettings(userId.toString());
            }
        }

        SettingsDataResponse response = new SettingsDataResponse();
        response.setData(settingsDTO.get());
        response.setMessage("success");
        response.setPath(BASE_PATH);
        response.setStatus(HttpStatus.OK.value());
        response.setTime(LocalDateTime.now());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> addSettings(Principal principal) throws AppException {
        
        StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
        
        settingsService.addSettingsToUser(userId.toString());

        NoDataResponse response = new NoDataResponse();
        response.setMessage("success");
        response.setStatus(HttpStatus.CREATED.value());
        response.setPath(BASE_PATH);
        response.setTime(LocalDateTime.now());

        return new ResponseEntity<NoDataResponse>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody SettingsDTO settings, Principal principal) throws AppException {
        
        StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}

        Optional<SettingsDTO> settingsDTO = settingsService.updateSettings(userId.toString(), settings);

        SettingsDataResponse response = new SettingsDataResponse();
        response.setData(settingsDTO.get());
        response.setMessage("success");
        response.setPath(BASE_PATH);
        response.setStatus(HttpStatus.OK.value());
        response.setTime(LocalDateTime.now());

        return new ResponseEntity<SettingsDataResponse>(response, HttpStatus.OK);
    }
    
}
