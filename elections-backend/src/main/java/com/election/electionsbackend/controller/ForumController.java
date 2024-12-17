package com.election.electionsbackend.controller;

import com.election.electionsbackend.dto.ForumRequest;
import com.election.electionsbackend.model.forum.Forum;
import com.election.electionsbackend.model.tags.ForumTags;
import com.election.electionsbackend.service.impl.ForumServiceImpl;
import com.election.electionsbackend.service.interfaces.ForumService;
import com.election.electionsbackend.service.interfaces.ForumTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing forum-related operations.
 */
@RestController
@RequestMapping("/forums")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumTagService forumTagService;
    @Autowired
    private ForumServiceImpl forumServiceImpl;

    /**
     * Creates a new forum.
     *
     * @param forumRequest the forum data to be created
     * @return the created Forum object, or null if creation fails
     */
    @PostMapping("/create")
    public Forum createForum(@RequestBody ForumRequest forumRequest) {
        if (forumRequest == null || forumRequest.getUserId() == null ||
                forumRequest.getTitle() == null || forumRequest.getContent() == null ||
                forumRequest.getTimestamp() == null) {
            System.out.println("Invalid forum data received.");
            return null;
        }

        System.out.println("Processing forum creation with data: " + forumRequest);
        return forumService.createForum(forumRequest);
    }

    /**
     * Retrieves a forum by its ID.
     *
     * @param id the ID of the forum
     * @return the Forum object, or null if not found
     */
    @GetMapping("/{id}")
    public Forum getForum(@PathVariable Long id) {
        if (id == null) {
            System.out.println("Forum ID is required.");
            return null;
        }

        return forumService.getForumById(id);
    }

    /**
     * Retrieves a paginated list of all forums.
     *
     * @param page the page number
     * @return a list of Forum objects on the specified page
     */
    @GetMapping("/all")
    public List<Forum> getAllForums(@RequestParam(required = false, defaultValue = "0") int page) {
        if (page < 0) {
            System.out.println("Invalid page number.");
            return List.of();
        }

        return forumService.getAllForums(page);
    }

    /**
     * Retrieves a paginated list of all forums sorted in ascending order.
     *
     * @param page the page number
     * @return a list of Forum objects sorted in ascending order
     */
    @GetMapping("/all/asc")
    public List<Forum> getAllForumsAsc(@RequestParam(required = false, defaultValue = "0") int page) {
        if (page < 0) {
            System.out.println("Invalid page number.");
            return List.of();
        }

        return forumService.getAllForumsASC(page);
    }

    /**
     * Retrieves a paginated list of all forums sorted in descending order.
     *
     * @param page the page number
     * @return a list of Forum objects sorted in descending order
     */
    @GetMapping("/all/desc")
    public List<Forum> getAllForumsDesc(@RequestParam(required = false, defaultValue = "0") int page) {
        if (page < 0) {
            System.out.println("Invalid page number.");
            return List.of();
        }

        return forumService.getAllForumsDESC(page);
    }

    /**
     * Retrieves all forum tags.
     *
     * @return a list of all ForumTags
     */
    @GetMapping("/tags/all")
    public List<ForumTags> getForumTags() {
        return forumTagService.getAllTags();
    }

    /**
     * Deletes a forum post by its ID.
     *
     * @param id the ID of the forum post to delete
     * @return true if the deletion was successful, false otherwise
     */
    @DeleteMapping("/delete/{id}")
    public boolean deleteForum(@PathVariable Long id) {
        System.out.println("Deleting forum with ID: " + id);
        return forumServiceImpl.deleteForumById(id);
    }

}
