package com.springboot.blog.controller;

import com.springboot.blog.payload.TagRequestDto;
import com.springboot.blog.payload.TagResponseDto;
import com.springboot.blog.service.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "http://localhost:3000")
public class TagController {

    TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Build add category REST API
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping
//    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<TagResponseDto> addTag(@Valid @RequestBody TagRequestDto tagRequestDto){
        TagResponseDto savedTag = tagService.createTag(tagRequestDto);
        return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
    }

    // Build get top 5 tags REST API
    @GetMapping("/top5")
    public ResponseEntity<List<TagResponseDto>> getTop5TagsByName(String name){
        return new ResponseEntity<>(tagService.getTop5TagsByName(name), HttpStatus.OK);
    }

    // Build delete tag REST API
//    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable(value = "id") Long tagId){
        tagService.deleteTagById(tagId);
        return new ResponseEntity<>("Tag deleted successfully!.", HttpStatus.OK);
    }

}
