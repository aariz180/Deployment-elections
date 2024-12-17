package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.dto.MessageRequest;
import com.election.electionsbackend.model.user.Message;

import java.util.List;

public interface MessageService {
    Message createMessage(MessageRequest messageRequest);
    List<Message> getMessagesByForum(Long forumId);
    boolean deleteMessageById(Long id);
}
