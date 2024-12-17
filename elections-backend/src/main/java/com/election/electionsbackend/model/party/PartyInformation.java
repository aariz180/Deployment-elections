package com.election.electionsbackend.model.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "party_information")
public class PartyInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(name = "party_name", length = 255, nullable = false)
    private String partyName;

    @Column(name = "party_origin", length = 255)
    private String partyOrigin;

    @Column(name = "party_orientation", length = 255)
    private String partyOrientation;

    @Column(name = "party_summary", length = 1000)
    private String partySummary;

    @Column(name = "party_history", length = 1000)
    private String partyHistory;

    @Column(name = "party_future", length = 1000)
    private String partyFuture;

    @Column(name = "party_ecological", length = 1000)
    private String partyEcological;
}
