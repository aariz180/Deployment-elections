package com.election.electionsbackend.model.forum;

import com.election.electionsbackend.model.user.Message;
import com.election.electionsbackend.model.user.User;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Forum entity representing a discussion thread in the forum.
 * It stores information about the forum post, related messages, categories, and the user who created the post.
 */
@Entity
@Getter
@Setter
@Table(name = "forum")
public class Forum {

    /**
     * The unique identifier for the forum post.
     * It is automatically generated using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The main content of the forum post.
     */
    private String content;
;

    /**
     * The timestamp representing when the post was created.
     */
    private LocalDateTime timestamp;

    /**
     * The title of the forum post.
     */
    private String title;

    /**
     * The list of messages (comments or replies) associated with this forum post.
     *
     * - Mapped by the 'forum' field in the `Message` entity.
     * - Uses LAZY fetching to load messages only when explicitly accessed.
     * - CascadeType.ALL ensures that any operations (persist, remove) on the Forum also affect its related messages.
     */
    @JsonManagedReference("forum-messages") // Avoids circular references in JSON serialization
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "forum")
    private List<Message> messages;

    /**
     * The list of categories to which this forum post belongs.
     *
     * - Mapped by the 'forum' field in the `ForumCategory` entity.
     * - Uses LAZY fetching to load categories only when accessed.
     * - CascadeType.ALL allows the cascading of all operations (save, update, delete) to the related `ForumCategory` entities.
     */
    @JsonManagedReference("forum-categories")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "forum")
    public Set<ForumCategory> forumCategories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
