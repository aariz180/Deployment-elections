package com.election.electionsbackend.model.news;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PoliticalNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgPath;
    private String title;
    private String content;

    @OneToMany(mappedBy = "politicalNews")
    private List<PoliticalNewsCategory> politicalNewsCategories;

    // Getters and Setters
}
