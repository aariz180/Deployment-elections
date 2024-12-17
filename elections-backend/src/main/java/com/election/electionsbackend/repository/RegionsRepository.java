package com.election.electionsbackend.repository;

import com.election.electionsbackend.model.Region;
import com.election.electionsbackend.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionsRepository extends JpaRepository<Region, Long> {

}
