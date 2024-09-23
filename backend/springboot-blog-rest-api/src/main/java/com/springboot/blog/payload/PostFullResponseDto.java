package com.springboot.blog.payload;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostFullResponseDto {

    private Long id;

    private UserVerySimpleResponseDto user;

    private String title;

    private String content;

    private List<CommentResponseDto> comments;

    private int commentCount = 0;

    private int likeCount = 0;

    private int dislikeCount = 0;

    private int viewerCount = 0;

    private List<TagResponseDto> tags;

    private LocalDateTime lastUpdated;
}
