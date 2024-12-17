package com.election.electionsbackend.repository;

import com.election.electionsbackend.model.votes.CandidateOverview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateOverviewRepository extends JpaRepository<CandidateOverview, Long> {
    List<CandidateOverview> findAllByPartyName(String partyName);
}
