package com.election.electionsbackend.repository;

import com.election.electionsbackend.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT v.candidate.candidateID AS candidateID, SUM(v.waarde) AS totalVotes " +
            "FROM Vote v GROUP BY v.candidate.candidateID")
    List<Object[]> getCandidateVoteSummary();

    @Query("SELECT v.region.regioCode AS regionCode, SUM(v.waarde) AS totalVotes " +
            "FROM Vote v GROUP BY v.region.regioCode")
    List<Object[]> getRegionalVoteSummary();

    // In VoteRepository
    @Query("SELECT r.ouderRegioCode AS provinceCode, SUM(v.waarde) AS totalVotes " +
            "FROM Vote v JOIN v.region r " +
            "WHERE v.candidate.candidateID = :candidateId AND r.ouderRegioCode IS NOT NULL " +
            "GROUP BY r.ouderRegioCode")
    List<Object[]> getCandidateProvinceVoteSummary(@Param("candidateId") int candidateId);


}
