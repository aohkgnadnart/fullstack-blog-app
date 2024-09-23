package com.springboot.blog.service;

import com.springboot.blog.payload.TagRequestDto;
import com.springboot.blog.payload.TagResponseDto;

import java.util.List;

public interface TagService {

    TagResponseDto createTag(TagRequestDto tagRequestDto);

    TagResponseDto getTagById(long id);

    void deleteTagById(long id);

    List<TagResponseDto> getTop5TagsByName(String name);
}
