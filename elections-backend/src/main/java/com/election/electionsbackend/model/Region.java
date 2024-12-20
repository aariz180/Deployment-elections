package com.election.electionsbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "regions")
@JsonIgnoreProperties
@Getter
@Setter
@NoArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private int regionID;

    @Column(name = "regio")
    private String regio;

    @Column(name = "regio_code", unique = true, length = 255)
    private String regioCode;

    @Column(name = "ouder_regio_code")
    private String ouderRegioCode;

    @Column(name = "groot_ouder_regio_code")
    private String grootOuderRegioCode;

    // Constructors, getters, and setters will be generated by Lombok
}
