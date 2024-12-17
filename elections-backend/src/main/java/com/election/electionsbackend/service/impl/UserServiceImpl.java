package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.repository.UserRepository;
import com.election.electionsbackend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsService getUserDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
            }
        };
    }

    // CREATE or UPDATE User
    public User createOrUpdateUser(User user) {
        if (user.getId() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // READ User by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // READ All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // DELETE User by ID
    public boolean deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // UPDATE User (Full update)
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            // Only update the fields that are provided in the request
            if (userDetails.getFullName() != null) {
                user.setFullName(userDetails.getFullName());
            }
            if (userDetails.getEmail() != null) {
                user.setEmail(userDetails.getEmail());
            }
            if (userDetails.getPassword() != null) {
                user.setPassword(userDetails.getPassword());  // You should hash the password before saving it
            }
            if (userDetails.getGender() != null) {
                user.setGender(userDetails.getGender());
            }
            if (userDetails.getVoted() != null) {
                user.setVoted(userDetails.getVoted());
            }
            if (userDetails.getAbout() != null) {
                user.setAbout(userDetails.getAbout());
            }
            if (userDetails.getColour() != null) {
                user.setColour(userDetails.getColour());
            }
            if (userDetails.getInitials() != null) {
                user.setInitials(userDetails.getInitials());
            }
            user.setUpdatedAt(LocalDateTime.now()); // Update the timestamp
            return userRepository.save(user); // Save and return the updated user
        }
        return null; // If user is not found
    }

}
