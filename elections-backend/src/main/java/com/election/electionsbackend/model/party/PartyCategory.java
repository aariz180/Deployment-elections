package com.election.electionsbackend.model.party;

import com.election.electionsbackend.model.tags.PartyTags;
import jakarta.persistence.*;

@Entity
public class PartyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private PartyOverview partyOverview;

    @ManyToOne
    @JoinColumn(name = "party_tag_id")
    private PartyTags partyTags;

    // Getters and Setters

}
