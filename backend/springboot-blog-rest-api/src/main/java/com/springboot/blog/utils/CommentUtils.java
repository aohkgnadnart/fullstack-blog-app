package com.springboot.blog.utils;

import com.springboot.blog.payload.CommentResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentUtils {
    public static List<CommentResponseDto> convertToCommentTree(List<CommentResponseDto> commentResponseDtos) {
        Map<Long, CommentResponseDto> commentMap = new HashMap<>();
        List<CommentResponseDto> rootComments = new ArrayList<>();

        for (CommentResponseDto commentResponseDto : commentResponseDtos) {
            commentMap.put(commentResponseDto.getId(), commentResponseDto);
            if (commentResponseDto.getParentComment() == null) {
                rootComments.add(commentResponseDto);
            } else {
                CommentResponseDto parentComment = commentMap.get(commentResponseDto.getParentComment().getId());
                if (parentComment != null) {
                    parentComment.getReplies().add(commentResponseDto);
                }
            }
        }
        return rootComments;
    }
}
