package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Tag;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostFullResponseDto;
import com.springboot.blog.payload.TagRequestDto;
import com.springboot.blog.payload.TagResponseDto;
import com.springboot.blog.repository.TagRepository;
import com.springboot.blog.service.TagService;
import com.springboot.blog.utils.AppConstants;
import com.springboot.blog.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    TagRepository tagRepository;

    ModelMapper mapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public TagResponseDto createTag(@Valid TagRequestDto tagRequestDto) {

        // Kiểm tra tag đã tồn tại
        if(tagRepository.existsByName(tagRequestDto.getName())){
            throw new BlogAPIException(HttpStatus.CONFLICT, "Name already exists: " + tagRequestDto.getName());
        }

        Tag tag= mapper.map(tagRequestDto, Tag.class);
        Tag newTag = tagRepository.save(tag);

        return mapper.map(newTag, TagResponseDto.class);

    }

    @Override
    public TagResponseDto getTagById(long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", Long.toString(id)));
        return mapper.map(tag, TagResponseDto.class);
    }

    @Override
    public void deleteTagById(long id) {

        // get tag by id from database
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", Long.toString(id)));

        // kiem tra xem role cua currentUsernam co phai admin khong
        if(!SecurityUtils.hasRole(AppConstants.ROLE_ADMIN)){
            throw new BlogAPIException(HttpStatus.FORBIDDEN, "You do not have permission to delete this post");
        }

        // kiem tra xem co post nao su dung tag nay khong
        if(!tag.getPosts().isEmpty()){
            throw new BlogAPIException(HttpStatus.CONFLICT, "Tag is in use by one or more posts, cannot delete");
        }

        tagRepository.delete(tag);
    }

    @Override
    public List<TagResponseDto> getTop5TagsByName(String name) {
        List<Tag> tags = tagRepository.findTop5ByNameContains(name);
        return tags.stream().map(tag -> mapper.map(tag, TagResponseDto.class)).collect(Collectors.toList());
    }
}
