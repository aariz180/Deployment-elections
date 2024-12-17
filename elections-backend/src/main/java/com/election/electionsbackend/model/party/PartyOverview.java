package com.election.electionsbackend.model.party;

import jakarta.persistence.*;
import lombok.Data;

@Entity //Indicates that this class is a JPA entity, which means it's mapped to a database table.
@Data // This annotation from the Lombok library automatically generates getters, setters.
@Table(name = "party_overview")
public class PartyOverview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary key to be generated automatically
    private Long partyId;

    private String partyName;
    private String partyOrigin;
    private String partyOrientation;
    private String partyDescription;
    private String partySummary;
    private Integer partyEcoscore;

    // Getters and Setters
}
