package com.election.electionsbackend.controller;

import com.election.electionsbackend.model.Party;
import com.election.electionsbackend.model.Vote;
import com.election.electionsbackend.model.user.User;
import com.election.electionsbackend.service.impl.PartiesServiceImpl;
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
@RequestMapping("/api/v1/elections/parties")
@RequiredArgsConstructor
public class PartiesController {

    @Autowired
    private PartiesServiceImpl partiesServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<List<Party>> getAllParties() {
        List<Party> party = partiesServiceImpl.getAllParties();
        return new ResponseEntity<>(party, HttpStatus.OK);
    }

}
