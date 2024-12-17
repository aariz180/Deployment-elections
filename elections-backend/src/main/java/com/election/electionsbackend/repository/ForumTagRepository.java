package com.election.electionsbackend.repository;

import com.election.electionsbackend.model.tags.ForumTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link ForumTags} entities in the database.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
@Repository
public interface ForumTagRepository extends JpaRepository<ForumTags, Integer> {
}
