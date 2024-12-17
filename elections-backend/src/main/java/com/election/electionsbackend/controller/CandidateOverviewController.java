package com.election.electionsbackend.controller;

import com.election.electionsbackend.model.votes.CandidateOverview;
import com.election.electionsbackend.service.interfaces.CandidateOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateOverviewController {

        private final CandidateOverviewService candidateOverviewService;

        @Autowired //Injects a Spring-managed bean (like `ProvinceVoteService`) into the class
        public CandidateOverviewController(CandidateOverviewService candidateOverviewService) {
            this.candidateOverviewService = candidateOverviewService;
        }

        @GetMapping("/{partyName}")
        public List<CandidateOverview> getCandidateOverview(@PathVariable String partyName) {
            System.out.println("Candidates received in controller: " + partyName);
            List<CandidateOverview> data = candidateOverviewService.getCandidateOverview(partyName);
            System.out.println("Candidates fetched in controller: " + data);
            return data;
        }
    }

