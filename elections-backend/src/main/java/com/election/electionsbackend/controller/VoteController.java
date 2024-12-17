package com.election.electionsbackend.controller;

import com.election.electionsbackend.dto.CandidateVoteSummary;
import com.election.electionsbackend.dto.RegionVoteSummary;
import com.election.electionsbackend.model.Vote;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.service.impl.UserServiceImpl;
import com.election.electionsbackend.service.impl.VoteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/elections/vote")
@RequiredArgsConstructor
public class VoteController {

    @Autowired
    private VoteServiceImpl voteServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> vote = voteServiceImpl.getAllVotes();
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }

    @GetMapping("/candidate-summary")
    public ResponseEntity<List<CandidateVoteSummary>> getCandidateVoteSummary() {
        List<CandidateVoteSummary> candidateVoteSummaries = voteServiceImpl.getCandidateVoteSummary()   ;
        return new ResponseEntity<>(candidateVoteSummaries, HttpStatus.OK);
    }

    @GetMapping("/regional-summary")
    public ResponseEntity<List<RegionVoteSummary>> getRegionalVoteSummary() {
        List<RegionVoteSummary> regionalSummaries = voteServiceImpl.getRegionalVoteSummary();
        return new ResponseEntity<>(regionalSummaries, HttpStatus.OK);
    }

    @GetMapping("/candidate-regional-summary/{candidateId}")
    public ResponseEntity<List<RegionVoteSummary>> getCandidateRegionalVoteSummary(
            @PathVariable int candidateId) {
        List<RegionVoteSummary> summaries = voteServiceImpl.getCandidateRegionalVoteSummary(candidateId);
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }


}
