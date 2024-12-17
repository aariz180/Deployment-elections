package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.dto.CandidateRegionalVoteSummary;
import com.election.electionsbackend.dto.CandidateVoteSummary;
import com.election.electionsbackend.dto.RegionVoteSummary;
import com.election.electionsbackend.model.Vote;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.repository.UserRepository;
import com.election.electionsbackend.repository.VoteRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;


    // READ All Users
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public List<CandidateVoteSummary> getCandidateVoteSummary() {
        List<Object[]> results = voteRepository.getCandidateVoteSummary();
        return results.stream()
                .map(result -> new CandidateVoteSummary((int) result[0], (long) result[1]))
                .collect(Collectors.toList());
    }

    public List<RegionVoteSummary> getRegionalVoteSummary() {
        List<Object[]> results = voteRepository.getRegionalVoteSummary();
        return results.stream()
                .map(result -> new RegionVoteSummary((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }
    @Override
    public List<RegionVoteSummary> getCandidateRegionalVoteSummary(int candidateId) {
        List<Object[]> results = voteRepository.getCandidateProvinceVoteSummary(candidateId);
        return results.stream()
                .map(result -> new RegionVoteSummary(
                        (String) result[0], // regionCode
                        (Long) result[1]    // totalVotes
                ))
                .collect(Collectors.toList());
    }


}
