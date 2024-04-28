package com.task.resource.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.resource.model.Settings;

public interface SettingsRepository extends MongoRepository<Settings, String> {
    
    Optional<Settings> findByUserId(String userId);
}
