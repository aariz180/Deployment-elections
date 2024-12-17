package com.election.electionsbackend.controller;

import com.election.electionsbackend.model.Region;
import com.election.electionsbackend.model.Vote;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.service.impl.RegionsServiceImpl;
import com.election.electionsbackend.service.impl.UserServiceImpl;
import com.election.electionsbackend.service.impl.VoteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/elections/regions")
@RequiredArgsConstructor
public class RegionsController {

    @Autowired
    private RegionsServiceImpl regionsServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<List<Region>> getAllRegions() {
        List<Region> region = regionsServiceImpl.getAllRegions();
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

}
