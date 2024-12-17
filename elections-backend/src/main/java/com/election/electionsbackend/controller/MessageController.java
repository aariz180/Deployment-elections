package com.election.electionsbackend.controller;

import com.election.electionsbackend.dto.MessageRequest;
import com.election.electionsbackend.model.user.Message;
import com.election.electionsbackend.service.impl.MessageServiceImpl;
import com.election.electionsbackend.service.interfaces.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling message-related operations in the application.
 * Provides endpoints for creating, retrieving, and deleting messages.
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final MessageServiceImpl messageServiceImpl;

    /**
     * Constructor for dependency injection.
     *
     * @param messageService The service to handle message-related logic.
     * @param messageServiceImpl The implementation of the MessageService.
     */
    public MessageController(MessageService messageService, MessageServiceImpl messageServiceImpl) {
        this.messageService = messageService;
        this.messageServiceImpl = messageServiceImpl;
    }


    /**
     * Creates a new message.
     * This endpoint takes a {@link MessageRequest} object in the request body,
     * creates a new message, and returns the created {@link Message}.
     *
     * @param messageRequest The request object containing the message details.
     * @return The created {@link Message}.
     */
    @PostMapping("/create")
    public Message createMessage(@RequestBody MessageRequest messageRequest) {
        return messageService.createMessage(messageRequest);
    }

    /**
     * Retrieves a list of messages associated with a specific forum.
     * This endpoint returns a list of {@link Message} objects that are part of the
     * forum specified by the given forum ID.
     *
     * @param forumId The ID of the forum whose messages are to be retrieved.
     * @return A list of {@link Message} objects for the specified forum.
     */
    @GetMapping("/forum/{forumId}")
    public List<Message> getMessagesByForum(@PathVariable Long forumId) {
        return messageService.getMessagesByForum(forumId);
    }

    /**
     * Deletes a message by its ID.
     * This endpoint deletes the message identified by the provided ID. It logs a message
     * indicating whether the deletion was successful or not.
     *
     * @param id The ID of the message to be deleted.
     * @return {@code true} if the message was successfully deleted, {@code false} if no
     *         message was found with the given ID.
     */
    @DeleteMapping("/delete/{id}")
    public boolean deleteMessage(@PathVariable Long id) {
        System.out.println("Deleting message with ID: " + id);
        return messageServiceImpl.deleteMessageById(id);
    }

    /**
     * Handles the HTTP POST request to update a message by its ID.
     *
     * @param id The ID of the message to update.
     * @param messageRequest The message object containing the new content for the message.
     * @return A boolean indicating whether the update was successful.
     */
    @PostMapping("/update/{id}")
    public boolean updateMessage(@PathVariable Long id, @RequestBody Message messageRequest) {
        System.out.println("updating message with ID: " + id);
        return messageServiceImpl.updateMessage(id, messageRequest);
    }


}
