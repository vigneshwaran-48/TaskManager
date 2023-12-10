package com.task.resource.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.resource.model.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    
    Optional<Settings> findByUserId(String userId);
}
