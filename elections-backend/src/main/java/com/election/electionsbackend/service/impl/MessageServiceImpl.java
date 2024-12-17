package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.dto.MessageRequest;
import com.election.electionsbackend.model.forum.Forum;
import com.election.electionsbackend.model.user.Message;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.repository.ForumRepository;
import com.election.electionsbackend.repository.MessageRepository;
import com.election.electionsbackend.repository.UserRepository;
import com.election.electionsbackend.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link MessageService} interface that handles operations
 * related to creating and retrieving messages in the forum.
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new {@link MessageServiceImpl} with the given repositories.
     *
     * @param messageRepository the {@link MessageRepository} used for message operations
     * @param forumRepository the {@link ForumRepository} used to retrieve forum data
     * @param userRepository the {@link UserRepository} used to retrieve user data
     */
    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ForumRepository forumRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new message and associates it with the specified forum and user.
     *
     * @param messageRequest the {@link MessageRequest} containing the data to create the message
     * @return the newly created {@link Message}, or {@code null} if any error occurs (e.g. forum or user not found)
     */
    @Override
    public Message createMessage(MessageRequest messageRequest) {
        if (messageRequest == null) {
            System.out.println("Message request is null!");
            return null;
        }

        Forum forum = forumRepository.findById(messageRequest.getForumId()).orElse(null);
        if (forum == null) {
            System.out.println("Forum with ID " + messageRequest.getForumId() + " not found!");
            return null;
        }

        User user = userRepository.findById(messageRequest.getUserId()).orElse(null);
        if (user == null) {
            System.out.println("User with ID " + messageRequest.getUserId() + " not found!");
            return null;
        }

        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setTimestamp(messageRequest.getTimestamp());
        message.setForum(forum);
        message.setUser(user);

        return messageRepository.save(message);
    }

    /**
     * Retrieves a list of messages associated with a specific forum.
     *
     * @param forumId the ID of the forum for which to retrieve the messages
     * @return a {@link List} of {@link Message} objects associated with the specified forum
     */
    @Override
    public List<Message> getMessagesByForum(Long forumId) {

        List<Message> messages = messageRepository.findByForumId(forumId);

        for (Message message : messages) {

            User user = message.getUser();
            message.setUser(user);

            if (user == null) {
                System.out.println("Warning!: Corrupted message row, no user found for message: " + message.getId());
            }
        }

        return messages;
    }

    /**
     * Deletes a message from the repository by its ID.
     *
     * @param id The ID of the message to be deleted.
     * @return {@code true} if the message was successfully deleted, {@code false} if no message
     *         with the given ID was found.
     */
    @Override
    public boolean deleteMessageById(Long id) {
        System.out.println("Deleting message with ID " + id);
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            messageRepository.deleteById(id);
            System.out.println("Message with ID " + id + " deleted!");
            return true;
        }
        System.out.println("Message with ID " + id + " not found!");
        return false;
    }

    /**
     * Updates an existing message with the provided content and timestamp.
     *
     * @param id The ID of the message to update.
     * @param updatedMessage The {@link Message} object containing the new content and/or timestamp.
     * @return {@code true} if the message was successfully updated, {@code false} if the message with the given ID was not found.
     */
    public Boolean updateMessage(Long id, Message updatedMessage) {
        Message message = messageRepository.findById(id).orElse(null);
        if (message == null) {
            System.out.println("Message with ID " + id + " not found!");
            return false;
        }

        if (updatedMessage.getContent() != null && !updatedMessage.getContent().trim().isEmpty()) {
            message.setContent(updatedMessage.getContent());
        } else {
            System.out.println("No content update for message ID " + id);
        }

        if (updatedMessage.getTimestamp() != null) {
            message.setTimestamp(updatedMessage.getTimestamp());
        } else {
            System.out.println("No timestamp update for message ID " + id);
        }

        // Save updated message
        messageRepository.save(message);

        System.out.println("Message with ID " + id + " updated successfully!");
        return true;
    }



}
