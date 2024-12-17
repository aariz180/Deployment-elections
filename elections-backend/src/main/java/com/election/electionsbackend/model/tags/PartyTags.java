package com.election.electionsbackend.model.tags;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PartyTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(mappedBy = "partyTags")
    private List<UserPartyTags> userPartyTags;

    // Getters and Setters
}
