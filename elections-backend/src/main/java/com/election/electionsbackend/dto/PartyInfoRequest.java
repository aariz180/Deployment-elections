package com.election.electionsbackend.dto;

import lombok.Data;

@Data
public class PartyInfoRequest {

    private String name;
    private String orientation;
    private String description;
    private String dateOfOrigin;
}
