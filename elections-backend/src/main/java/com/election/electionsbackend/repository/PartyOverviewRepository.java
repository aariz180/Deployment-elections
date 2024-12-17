package com.election.electionsbackend.repository;

import com.election.electionsbackend.model.party.PartyOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Java Persistence API for Database Access. Automatically creates a table
public interface PartyOverviewRepository extends JpaRepository<PartyOverview, Long> {

}

