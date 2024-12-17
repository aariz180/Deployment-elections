package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.model.forum.ForumCategory;
import com.election.electionsbackend.repository.ForumCategoryRepository;
import com.election.electionsbackend.service.interfaces.ForumCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumCategoryServiceImpl implements ForumCategoryService {

    private final ForumCategoryRepository forumCategoryRepository;

    @Autowired
    public ForumCategoryServiceImpl(ForumCategoryRepository forumCategoryRepository) {
        this.forumCategoryRepository = forumCategoryRepository;
    }


    @Override
    public ForumCategory createForumCategory(ForumCategory forumCategory) {
        return forumCategoryRepository.save(forumCategory);
    }
}
