package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.model.Party;
import com.election.electionsbackend.model.Vote;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.repository.PartiesRepository;
import com.election.electionsbackend.repository.UserRepository;
import com.election.electionsbackend.repository.VoteRepository;
import com.election.electionsbackend.service.interfaces.PartiesService;
import com.election.electionsbackend.service.interfaces.UserService;
import com.election.electionsbackend.service.interfaces.VoteService;
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
public class PartiesServiceImpl implements PartiesService {
    @Autowired
    private PartiesRepository partiesRepository;


    // READ All Users
    public List<Party> getAllParties() {
        return partiesRepository.findAll();
    }

}
