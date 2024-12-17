package com.election.electionsbackend.model.tags;

import com.election.electionsbackend.model.news.PoliticalNewsCategory;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class NewsTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(mappedBy = "newsTags")
    private List<PoliticalNewsCategory> politicalNewsCategories;

    // Getters and Setters
}



