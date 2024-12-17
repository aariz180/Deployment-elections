package com.election.electionsbackend.model.news;

import com.election.electionsbackend.model.tags.NewsTags;
import jakarta.persistence.*;

@Entity
public class PoliticalNewsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "news_tag_id")
    private NewsTags newsTags;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private PoliticalNews politicalNews;

    // Getters and Setters
}
