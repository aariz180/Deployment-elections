package com.election.electionsbackend.model.tags;

import com.election.electionsbackend.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_party_tags")
public class UserPartyTags {
    @Id
    @JsonBackReference("user-tags")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private PartyTags partyTags;

    // Getters and Setters
}
