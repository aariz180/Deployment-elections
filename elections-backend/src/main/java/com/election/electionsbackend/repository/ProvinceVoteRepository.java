package com.election.electionsbackend.repository;

import com.election.electionsbackend.model.votes.ProvinceVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceVoteRepository extends JpaRepository<ProvinceVotes, Long> {
    List<ProvinceVotes> findAllByPartyName(String partyName);
}
