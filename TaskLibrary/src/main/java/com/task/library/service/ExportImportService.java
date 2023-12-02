package com.task.library.service;

import java.util.Optional;

import com.task.library.exception.AppException;

public interface ExportImportService {
    
    Optional<byte[]> exportData(String userId) throws AppException;

    void importData(String userId, byte[] data) throws AppException;
}
