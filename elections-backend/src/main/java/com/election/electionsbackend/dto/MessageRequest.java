package com.election.electionsbackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRequest {
    private String content;
    private LocalDateTime timestamp;
    private Long forumId;
    private Long userId;
}
