package com.election.electionsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateRegionalVoteSummary {
    private int candidateID;
    private String regionCode;
    private long totalVotes;
    }