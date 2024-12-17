package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.dto.CandidateRegionalVoteSummary;
import com.election.electionsbackend.dto.CandidateVoteSummary;
import com.election.electionsbackend.dto.RegionVoteSummary;
import com.election.electionsbackend.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface VoteService {
    List<Vote> getAllVotes();
    List<CandidateVoteSummary> getCandidateVoteSummary();
    List<RegionVoteSummary> getRegionalVoteSummary();
    List<RegionVoteSummary> getCandidateRegionalVoteSummary(int candidateId);

}
