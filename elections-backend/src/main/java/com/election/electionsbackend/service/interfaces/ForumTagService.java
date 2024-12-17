package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.model.forum.ForumCategory;
import com.election.electionsbackend.model.tags.ForumTags;

import java.util.List;

/**
 * Service interface for managing forum tags.
 * Provides methods for retrieving tags related to the forum.
 */
public interface ForumTagService {

    /**
     * Retrieves all tags available for the forum.
     *
     * @return a list of all {@link ForumTags} objects
     */
    List<ForumTags> getAllTags();
}
