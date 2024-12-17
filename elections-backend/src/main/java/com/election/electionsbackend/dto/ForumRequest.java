package com.election.electionsbackend.dto;

import com.election.electionsbackend.model.user.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ForumRequest {
    private String content;
    private LocalDateTime timestamp;
    private String title;
    private Long userId;
    private Set<Integer> forumTagIds;
}
