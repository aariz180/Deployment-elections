package com.election.electionsbackend.controller;

import com.election.electionsbackend.model.votes.ProvinceVotes;
import com.election.electionsbackend.service.interfaces.ProvinceVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class ProvinceVoteController {

    private final ProvinceVoteService provinceVoteService;

    @Autowired //Injects a Spring-managed bean (like `ProvinceVoteService`) into the class,
    // allowing the framework to automatically provide the required dependency.
    public ProvinceVoteController(ProvinceVoteService provinceVoteService) {
        this.provinceVoteService = provinceVoteService;
    }

    @GetMapping("/{partyName}")
    public List<ProvinceVotes> getProvinceVotes(@PathVariable String partyName) {
        System.out.println("Party name received in controller: " + partyName);
        List<ProvinceVotes> data = provinceVoteService.getProvinceVotes(partyName);
        System.out.println("Province votes fetched in controller: " + data);
        return data;
    }
}
