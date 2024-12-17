package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.model.votes.ProvinceVotes;

import java.util.List;

public interface ProvinceVoteService {
    List<ProvinceVotes> getProvinceVotes(String partyName);
}
