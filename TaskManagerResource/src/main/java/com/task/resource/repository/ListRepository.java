package com.task.resource.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.resource.model.List;

import jakarta.transaction.Transactional;

public interface ListRepository extends MongoRepository<List, String> {

	Optional<List> findByUserIdAndListName(String userId, String listName);
	
	Optional<java.util.List<List>> findByListColor(String listColor);
	
	Optional<java.util.List<List>> findByUserId(String userId);
	
	Optional<List> findByListIdAndUserId(String listId, String userName);
	
	@Transactional
	java.util.List<List> deleteByUserIdAndListId(String userId, String listId);
}
