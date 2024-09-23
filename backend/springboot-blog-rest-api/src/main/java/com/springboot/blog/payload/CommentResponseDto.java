package com.springboot.blog.payload;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;

    private String content;

    private UserVerySimpleResponseDto user;

    private CommentSimpleResponseDto parentComment;

    private List<CommentResponseDto> replies;

    private LocalDateTime createAt;

}
