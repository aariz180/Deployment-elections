package com.election.electionsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionVoteSummary {
    private String regionCode;
    private long totalVotes;
}
