package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.model.votes.CandidateOverview;

import java.util.List;

public interface CandidateOverviewService {
    List<CandidateOverview> getCandidateOverview(String partyName);

}
