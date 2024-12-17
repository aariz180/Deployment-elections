package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.model.tags.ForumTags;
import com.election.electionsbackend.repository.ForumTagRepository;
import com.election.electionsbackend.service.interfaces.ForumTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link ForumTagService} interface.
 * Handles the business logic for managing forum tags.
 */
@Service
public class ForumTagServiceImpl implements ForumTagService {

    private final ForumTagRepository forumTagRepository;

    /**
     * Constructs a {@link ForumTagServiceImpl} with the given {@link ForumTagRepository}.
     *
     * @param forumTagRepository the repository to interact with forum tags
     */
    @Autowired
    public ForumTagServiceImpl(ForumTagRepository forumTagRepository) {
        this.forumTagRepository = forumTagRepository;
    }

    /**
     * Retrieves all tags from the repository.
     *
     * @return a list of {@link ForumTags} from the database
     */
    @Override
    public List<ForumTags> getAllTags() {
        return forumTagRepository.findAll();
    }
}
