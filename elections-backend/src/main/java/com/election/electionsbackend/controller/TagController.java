package com.election.electionsbackend.controller;

import com.election.electionsbackend.model.tags.Tag;
import com.election.electionsbackend.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/create")
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @GetMapping("/all")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }
}
