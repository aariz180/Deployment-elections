package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.model.votes.ProvinceVotes;
import com.election.electionsbackend.repository.ProvinceVoteRepository;
import com.election.electionsbackend.service.interfaces.ProvinceVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceVoteServiceImpl implements ProvinceVoteService {

    private final ProvinceVoteRepository provinceVoteRepository;

    @Autowired
    public ProvinceVoteServiceImpl(ProvinceVoteRepository provinceVoteRepository) {
        this.provinceVoteRepository = provinceVoteRepository;
    }

    @Override
    public List<ProvinceVotes> getProvinceVotes(String partyName) {
        if (partyName != null) {
            System.out.println("Fetching votes for party: " + partyName);
            List<ProvinceVotes> data = provinceVoteRepository.findAllByPartyName(partyName);

            if (data.isEmpty()) {
                System.out.println("No vote data found for party: " + partyName);
                return List.of(); // Return an empty list if no data is found
            }

            System.out.println("Province votes fetched in service: " + data);
            return data;
        } else {
            throw new IllegalArgumentException("Party name not provided");
        }
    }
}

