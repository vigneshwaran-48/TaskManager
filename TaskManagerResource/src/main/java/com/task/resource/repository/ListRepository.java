package com.task.resource.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.resource.model.List;

import jakarta.transaction.Transactional;

public interface ListRepository extends JpaRepository<List, Long> {

	Optional<List> findByUserIdAndListName(String userId, String listName);
	
	Optional<java.util.List<List>> findByListColor(String listColor);
	
	Optional<java.util.List<List>> findByUserId(String userId);
	
	Optional<List> findByListIdAndUserId(Long listId, String userName);
	
	@Transactional
	java.util.List<List> deleteByUserIdAndListId(String userId, Long listId);
}
