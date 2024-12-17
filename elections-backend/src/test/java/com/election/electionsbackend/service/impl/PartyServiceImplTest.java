package com.election.electionsbackend.service.impl;

import com.election.electionsbackend.model.party.PartyInformation;
import com.election.electionsbackend.model.party.PartyOverview;
import com.election.electionsbackend.repository.PartyInformationRepository;
import com.election.electionsbackend.repository.PartyOverviewRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PartyServiceImplTest {

    // Test for fetching all parties
    @Test
    void testGetAllParties() {
        // Mock the PartyOverviewRepository
        PartyOverviewRepository mockOverviewRepo = mock(PartyOverviewRepository.class);

        // Sample data
        PartyOverview party1 = new PartyOverview();
        party1.setPartyId(1L);
        party1.setPartyName("PVV");

        PartyOverview party2 = new PartyOverview();
        party2.setPartyId(2L);
        party2.setPartyName("NSC");

        // Mock behavior: return the sample data when findAll is called
        when(mockOverviewRepo.findAll()).thenReturn(List.of(party1, party2));

        // Create service instance with mocked dependencies
        PartyServiceImpl partyService = new PartyServiceImpl(mockOverviewRepo, mock(PartyInformationRepository.class));

        // Call the method to test
        List<PartyOverview> result = partyService.getAllParties();

        // Verify the result matches expectations
        assertEquals(2, result.size()); // Check list size
        assertEquals("PVV", result.get(0).getPartyName()); // Check first party name
        assertEquals("NSC", result.get(1).getPartyName()); // Check second party name

        // Ensure the repository's findAll was called once
        verify(mockOverviewRepo, times(1)).findAll();
    }

    // Test for fetching party information by name
    @Test
    void testGetPartyInformationByName() {
        // Mock the PartyInformationRepository
        PartyInformationRepository mockInfoRepo = mock(PartyInformationRepository.class);

        // Sample data
        String partyName = "D66";
        PartyInformation partyInfo = new PartyInformation();
        partyInfo.setPartyName(partyName);
        partyInfo.setPartySummary("Focused on ecology and environment.");

        // Mock behavior: return the sample data when findByPartyName is called
        when(mockInfoRepo.findByPartyName(partyName)).thenReturn(partyInfo);

        // Create service instance with mocked dependencies
        PartyServiceImpl partyService = new PartyServiceImpl(mock(PartyOverviewRepository.class), mockInfoRepo);

        // Call the method to test
        PartyInformation result = partyService.getPartyInformationByName(partyName);

        // Verify the result matches expectations
        assertEquals("D66", result.getPartyName());
        assertEquals("Focused on ecology and environment.", result.getPartySummary());

        // Ensure the repository's findByPartyName was called once
        verify(mockInfoRepo, times(1)).findByPartyName(partyName);
    }
}
