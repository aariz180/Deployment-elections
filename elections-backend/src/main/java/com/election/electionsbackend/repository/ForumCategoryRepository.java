package com.election.electionsbackend.repository;

import com.election.electionsbackend.model.forum.ForumCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Integer> {
}
