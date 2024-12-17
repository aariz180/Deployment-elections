package com.election.electionsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CandidateVoteSummary {
    private int candidateID;
    private long totalVotes;
}
