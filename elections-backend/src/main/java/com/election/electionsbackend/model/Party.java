package com.election.electionsbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parties")
@JsonIgnoreProperties
@Getter
@Setter
@NoArgsConstructor
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private int partyID;

    @Column(name = "lijst_nummer")
    private Integer lijstNummer;

    @Column(name = "lijst_naam")
    private String lijstNaam;

    // Constructors, getters, and setters will be generated by Lombok
}
