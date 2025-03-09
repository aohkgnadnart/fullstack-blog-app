package com.springboot.blog.payload;

import com.springboot.blog.entity.PostRevision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRevisionResponseDto {
    private Long id;
    private Long postId;
    private UserVerySimpleResponseDto user;
    private String title;
    private String content;
    private List<TagResponseDto> tags;
    private LocalDateTime createdAt;
    private PostRevision.RevisionType revisionType;
} 