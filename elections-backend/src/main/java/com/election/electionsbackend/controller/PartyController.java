package com.election.electionsbackend.controller;

import com.election.electionsbackend.model.party.PartyOverview;
import com.election.electionsbackend.model.party.PartyInformation;
import com.election.electionsbackend.service.interfaces.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parties")
public class PartyController {

    @Autowired
    private PartyService partyService;

    // Party overview
    @GetMapping("/all")
    public List<PartyOverview> getAllParties() {
        return partyService.getAllParties();
    }

    // Party info
    @GetMapping("/{partyName}")
    public PartyInformation getPartyInformationByName(@PathVariable String partyName) {
        // The @PathVariable binds the value of {partyName} in the URL path
        // to the String partyName parameter. For example, if the URL is /parties/VVD,
        // the partyName will be "VVD".
        PartyInformation data = partyService.getPartyInformationByName(partyName);
        System.out.println("Data returned" + data);
        return data;
    }
}

