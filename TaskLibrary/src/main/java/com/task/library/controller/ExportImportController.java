package com.task.library.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.task.library.dto.NoDataResponse;
import com.task.library.exception.AppException;
import com.task.library.service.ExportImportService;
import com.task.library.util.AuthUtil;

@RestController
@RequestMapping("/api/v1/data")
public class ExportImportController {

    private final static String NOT_AUTHENTICATED = "Not Authenticated";
    private final static String EXPORT_DATA_FILE_NAME = "attachment; filename=\"task_manager_export.data\"";
    private final static String BASE_PATH = "/api/v1/data";
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ExportImportController.class);
    
    @Autowired
    private ExportImportService exportImportService;

    @GetMapping("export")
    public ResponseEntity<?> exportData(Principal principal) throws AppException {

        StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
        
        Optional<byte[]> exportData = exportImportService.exportData(userId.toString());
        if(exportData.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, EXPORT_DATA_FILE_NAME)
                            .body(exportData.get());
    }

    @PostMapping("import")
    public ResponseEntity<?> importData(@RequestParam("data") MultipartFile file, Principal principal) throws AppException {

        StringBuffer userId = new StringBuffer(principal != null ? principal.getName() : "");
		if(!AuthUtil.getInstance().isValidUserId(userId)) {
			throw new AppException(NOT_AUTHENTICATED, HttpStatus.BAD_REQUEST.value());
		}
        
        try {
            exportImportService.importData(userId.toString(), file.getBytes());
        } 
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("Error while importing", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        NoDataResponse response = new NoDataResponse();
        response.setMessage("success");
        response.setStatus(200);
        response.setPath(BASE_PATH + "/import");
        response.setTime(LocalDateTime.now());

        return ResponseEntity.ok().body(response);
    }
}
