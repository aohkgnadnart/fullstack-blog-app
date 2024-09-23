package com.springboot.blog.payload;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostSimpleResponseDto {
    private Long id;

    private String title;

    private UserVerySimpleResponseDto user;

    private int viewerCount;

    private int likeCount;

    private int dislikeCount;

    private int commentCount;

    private LocalDateTime lastUpdated;

    private List<TagResponseDto> tags;
}
