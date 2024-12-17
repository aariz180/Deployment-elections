package com.election.electionsbackend.model.votes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "province_votes")
public class ProvinceVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mainId;

    private Integer provinceId;
    private String provinceName;
    private String partyName;
    private Float voteCount;
}
