package com.election.electionsbackend.service.interfaces;

import com.election.electionsbackend.model.tags.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(Tag tag);
    List<Tag> getAllTags();
}
