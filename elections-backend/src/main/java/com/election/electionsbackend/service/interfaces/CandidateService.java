package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.model.Candidate;
import com.election.electionsbackend.model.Vote;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface CandidateService {
    List<Candidate> getAllCandidates();
}
