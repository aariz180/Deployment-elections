package com.election.electionsbackend.controller;

import com.election.electionsbackend.model.Candidate;
import com.election.electionsbackend.model.Vote;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.service.impl.CandidateServiceImpl;
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
@RequestMapping("/api/v1/elections/candidate")
@RequiredArgsConstructor
public class CandidateController {

    @Autowired
    private CandidateServiceImpl candidateServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidate = candidateServiceImpl.getAllCandidates();
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

}
