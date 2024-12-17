package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.model.party.PartyOverview;
import com.election.electionsbackend.model.party.PartyInformation;
import com.election.electionsbackend.repository.PartyOverviewRepository;
import com.election.electionsbackend.repository.PartyInformationRepository;
import com.election.electionsbackend.service.interfaces.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyServiceImpl implements PartyService {

    private final PartyOverviewRepository partyOverviewRepository;
    private final PartyInformationRepository partyInformationRepository;

    @Autowired // It tells Spring to automatically inject the required info
    public PartyServiceImpl(PartyOverviewRepository partyOverviewRepository, PartyInformationRepository partyInformationRepository) {
        this.partyOverviewRepository = partyOverviewRepository;
        this.partyInformationRepository = partyInformationRepository;
    }

    // Party overview
    @Override //Tells the compiler that the method is intended to override a method in a superclass
    public List<PartyOverview> getAllParties() {
        return partyOverviewRepository.findAll();
    }

    // Party info
    @Override
    public PartyInformation getPartyInformationByName(String partyName) { // Uses the partyInformationRepository and calls findByPartyName(partyName)
        System.out.println("Fetching Party Information for: " + partyName);
        PartyInformation data = partyInformationRepository.findByPartyName(partyName);
        System.out.println("Fetched Data: " + data);
        return data;
    }
}
