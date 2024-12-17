package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.dto.ForumRequest;
import com.election.electionsbackend.model.forum.Forum;
import com.election.electionsbackend.model.forum.ForumCategory;
import com.election.electionsbackend.model.tags.ForumTags;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.repository.ForumCategoryRepository;
import com.election.electionsbackend.repository.ForumRepository;
import com.election.electionsbackend.repository.ForumTagRepository;
import com.election.electionsbackend.repository.UserRepository;
import com.election.electionsbackend.service.interfaces.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ForumService interface for managing forum posts and related categories and tags.
 */
@Service
public class ForumServiceImpl implements ForumService {

    /** Repository for forum entities. */
    private final ForumRepository forumRepository;

    /** Repository for forum categories, linking forums and tags. */
    private final ForumCategoryRepository forumCategoryRepository;

    /** Repository for tags associated with forums. */
    private final ForumTagRepository forumTagRepository;

    /** Repository for user entities. */
    private final UserRepository userRepository;

    /**
     * Constructs a ForumServiceImpl instance with injected repositories.
     *
     * @param forumRepository Repository for forums.
     * @param forumCategoryRepository Repository for forum categories.
     * @param userRepository Repository for users.
     * @param forumTagRepository Repository for tags associated with forums.
     */
    @Autowired
    public ForumServiceImpl(ForumRepository forumRepository, ForumCategoryRepository forumCategoryRepository,
                            UserRepository userRepository, ForumTagRepository forumTagRepository) {
        this.forumRepository = forumRepository;
        this.forumCategoryRepository = forumCategoryRepository;
        this.userRepository = userRepository;
        this.forumTagRepository = forumTagRepository;
    }

    /**
     * Creates a new forum based on the provided ForumRequest.
     * This method performs the following steps:
     * 1. Validates the ForumRequest to ensure required fields are present.
     * 2. Retrieves the User associated with the provided userId in the request.
     * 3. Initializes and saves a new Forum instance with content, title, and timestamp.
     * 4. If tagIds are provided, iterates through each to find the corresponding ForumTags.
     *    For each found tag, a ForumCategory instance is created, linking the Forum to the tag.
     * Any missing required information (userId, title, content, timestamp) results in an error
     * message being printed and the method returning {@code null}.
     *
     * @param forumRequest The ForumRequest object containing forum details.
     *                     Includes userId, title, content, timestamp, and tagIds.
     * @return The created Forum object if successful; {@code null} if validation fails.
     */
    @Override
    public Forum createForum(ForumRequest forumRequest) {
        if (forumRequest == null) {
            System.out.println("Forum request is null!");
            return null;
        }

        if (forumRequest.getUserId() == null) {
            System.out.println("User ID is required!");
            return null;
        }

        if (forumRequest.getContent() == null || forumRequest.getTitle() == null || forumRequest.getTimestamp() == null) {
            System.out.println("Content, title, and timestamp are required!");
            return null;
        }

        User user = userRepository.findById(forumRequest.getUserId()).orElse(null);
        if (user == null) {
            System.out.println("User with ID " + forumRequest.getUserId() + " not found.");
            return null;
        }

        Forum forum = new Forum();
        forum.setContent(forumRequest.getContent());
        forum.setTimestamp(forumRequest.getTimestamp());
        forum.setTitle(forumRequest.getTitle());
        forum.setUser(user);
        Forum savedForum = forumRepository.save(forum);

        if (forumRequest.getForumTagIds() == null || forumRequest.getForumTagIds().isEmpty()) {
            System.out.println("No tags provided.");
        } else {
            for (Integer tagId : forumRequest.getForumTagIds()) {
                ForumTags forumTag = forumTagRepository.findById(tagId).orElse(null);
                if (forumTag != null) {
                    ForumCategory forumCategory = new ForumCategory();
                    forumCategory.setForum(savedForum);
                    forumCategory.setForumTags(forumTag);
                    forumCategoryRepository.save(forumCategory);
                } else {
                    System.out.println("Tag with ID " + tagId + " not found.");
                }
            }
        }
        return savedForum;
    }

    /**
     * Retrieves a forum by its ID.
     *
     * @param id The ID of the forum.
     * @return The Forum object if found; {@code null} otherwise.
     */
    @Override
    public Forum getForumById(Long id) {
        return forumRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves all forums with pagination and sorting by timestamp in ascending order.
     *
     * @param page The page number to retrieve.
     * @return A list of Forum objects sorted by timestamp in ascending order.
     */
    @Override
    public List<Forum> getAllForumsASC(int page) {
        return getForumsWithUser(page, Sort.by(Sort.Direction.ASC, "timestamp"));
    }

    /**
     * Retrieves all forums with pagination and sorting by timestamp in descending order.
     *
     * @param page The page number to retrieve.
     * @return A list of Forum objects sorted by timestamp in descending order.
     */
    @Override
    public List<Forum> getAllForumsDESC(int page) {
        return getForumsWithUser(page, Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    /**
     * Retrieves all forums with pagination and no specific sorting.
     *
     * @param page The page number to retrieve.
     * @return A list of Forum objects with pagination.
     */
    @Override
    public List<Forum> getAllForums(int page) {
        return getForumsWithUser(page, Sort.unsorted());
    }

    /**
     * Helper method to retrieve forums with user data attached, with pagination and sorting.
     *
     * @param page The page number to retrieve.
     * @param sort The sorting criteria for the query (could be ascending or descending by timestamp).
     * @return A list of Forum objects with user data.
     */
    private List<Forum> getForumsWithUser(int page, Sort sort) {

        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<Forum> forumPage = forumRepository.findAll(pageable);
        List<Forum> forums = forumPage.getContent();

        for (Forum forum : forums) {

            User user = forum.getUser();
            forum.setUser(user);

            if (user == null) {

                System.out.println("Error: Corrupted forum row, no user found for forum: " + forum.getId());
            }
        }

        return forums;
    }

    /**
     * Deletes a forum post by its ID.
     * Searches for the forum post by its ID. If the post is found, it is deleted.
     * If the forum post with the given ID does not exist, no action is taken,
     * and a message indicating that the post was not found is logged.
     *
     * @param id the ID of the forum post to delete
     * @return true if the forum post was successfully deleted,
     *         false if no post was found with the given ID
     */
    public boolean deleteForumById(Long id) {
        System.out.println("ID is: " + id);
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            forumRepository.deleteById(id);
            System.out.println("Deleted forum with ID " + id + "!");
            return true;
        }
        System.out.println("Forum with ID " + id + " not found!");
        return false;
    }



//    TODO add update functionality
//
//    public Forum updateForum(Long id, ForumRequest forumRequest) {
//        Forum forum = forumRepository.findById(id).orElse(null);
//
//        if (forum == null) {
//            System.out.println("Forum with ID " + id + " not found.");
//            return null;
//        }
//
//        if (forumRequest.getContent() != null) {
//            forum.setContent(forumRequest.getContent());
//        }
//
//        if (forumRequest.getTitle() != null) {
//            forum.setTitle(forumRequest.getTitle());
//        }
//
//        if (forumRequest.getForumTagIds() != null) {
//            for (Integer tagId : forumRequest.getForumTagIds()) {
//                ForumTags forumTag = forumTagRepository.findById(tagId).orElse(null);
//                if (forumTag != null) {
//                    ForumCategory forumCategory = forumCategoryRepository.findById(id);
//                    forumCategory.setForum(savedForum);
//                    forumCategory.setForumTags(forumTag);
//                }
//            }
//        }
//
//
//
//    }

}
