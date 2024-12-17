package com.election.electionsbackend.model.votes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="candidates_overview")
public class CandidateOverview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    private String candidateName;
    private String partyName;
    private Integer seatPosition;
    private String candidateTitle;
}
