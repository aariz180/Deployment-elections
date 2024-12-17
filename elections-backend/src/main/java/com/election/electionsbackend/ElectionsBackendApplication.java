package com.election.electionsbackend;

import com.election.electionsbackend.model.user.Role;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class ElectionsBackendApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ElectionsBackendApplication.class, args);
    }

    public void run(String... args) throws Exception {
        List<User> adminAccounts = userRepository.findByRole(Role.ADMIN);
        if (adminAccounts.isEmpty()) {
            User user = new User();
            user.setEmail("admin@elections.com");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }
    }

    }

