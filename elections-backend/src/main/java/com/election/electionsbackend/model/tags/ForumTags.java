package com.election.electionsbackend.model.tags;

import com.election.electionsbackend.model.forum.ForumCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * ForumTags entity representing tags associated with forum categories.
 * This class manages the relationship between tags and their corresponding forum categories.
 */
@Entity
@Getter
@Setter
@Table(name = "forum_tags")
public class ForumTags {

    /**
     * The unique identifier for the forum tag.
     * It is automatically generated using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The tag associated with this ForumTags instance.
     * - Mapped by the `tag_id` foreign key.
     * - Uses `@JsonBackReference` to prevent circular references in JSON serialization.
     */
    @JsonBackReference("forum-tags-tag")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    /**
     * The list of forum categories that utilize this tag.
     * - Mapped by the `forumTags` property in the ForumCategory entity.
     * - Uses `@JsonBackReference` to prevent circular references in JSON serialization.
     */
    @JsonManagedReference("forum-tags")
    @OneToMany(mappedBy = "forumTags", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<ForumCategory> forumCategories;

    @JsonProperty("tagName")
    public String getForumTagName() {
        return tag != null ? tag.getName() : null;
    }

    @JsonProperty("tagId")
    public Long getTagId() {
        return tag != null ? tag.getId() : null;
    }


}
