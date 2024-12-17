package com.election.electionsbackend.repository;

import com.election.electionsbackend.model.party.PartyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyInformationRepository extends JpaRepository<PartyInformation, Long> {
    PartyInformation findByPartyName(String partyName);
}
