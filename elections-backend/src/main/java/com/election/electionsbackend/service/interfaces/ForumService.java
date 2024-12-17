package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.dto.ForumRequest;
import com.election.electionsbackend.model.forum.Forum;
import com.election.electionsbackend.model.tags.ForumTags;

import java.util.List;
import java.util.Set;

/**
 * Interface for managing forum-related operations.
 * This interface defines methods for creating and retrieving forums.
 */
public interface ForumService {

    /**
     * Creates a new forum.
     *
     * @param forumRequest Request the Forum object to be created
     * @return the created Forum object
     */
    Forum createForum(ForumRequest forumRequest);

    /**
     * Retrieves a forum by its ID.
     *
     * @param id the ID of the forum to retrieve
     * @return the Forum object corresponding to the given ID
     */
    Forum getForumById(Long id);

    /**
     * Retrieves a paginated list of all forums.
     *
     * @param page the page number for pagination
     * @return a list of all Forum objects on the specified page
     */
    List<Forum> getAllForums(int page);

    /**
     * Retrieves a paginated list of all forums sorted in ascending order.
     *
     * @param page the page number for pagination
     * @return a list of all Forum objects sorted in ascending order
     */
    List<Forum> getAllForumsASC(int page);

    /**
     * Retrieves a paginated list of all forums sorted in descending order.
     *
     * @param page the page number for pagination
     * @return a list of all Forum objects sorted in descending order
     */
    List<Forum> getAllForumsDESC(int page);
}
