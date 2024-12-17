package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.model.votes.CandidateOverview;
import com.election.electionsbackend.repository.CandidateOverviewRepository;
import com.election.electionsbackend.service.interfaces.CandidateOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateOverviewServiceImpl implements CandidateOverviewService {

    private final CandidateOverviewRepository candidateOverviewRepository;

    @Autowired
    public CandidateOverviewServiceImpl(CandidateOverviewRepository candidateOverviewRepository) {
        this.candidateOverviewRepository = candidateOverviewRepository;
    }

    @Override
    public List<CandidateOverview> getCandidateOverview(String partyName) {
        if (partyName != null) {
            System.out.println("Fetching candidates for party: " + partyName);
            List<CandidateOverview> data = candidateOverviewRepository.findAllByPartyName(partyName);

            if (data.isEmpty()) {
                System.out.println("No candidate data found for party: " + partyName);
                return List.of(); // Return an empty list if no data is found
            }

            System.out.println("Candidates fetched in service: " + data);
            return data;
        } else {
            throw new IllegalArgumentException("Candidates not provided");
        }
    }
}
