package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.model.party.PartyOverview;
import com.election.electionsbackend.model.party.PartyInformation;

import java.util.List;

// It provides methods for retrieving party overviews.
public interface PartyService {
    // Party overview
    List<PartyOverview> getAllParties();

    // Party info
    PartyInformation getPartyInformationByName(String partyName);
}
