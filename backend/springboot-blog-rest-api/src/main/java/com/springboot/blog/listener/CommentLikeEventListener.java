package com.springboot.blog.listener;

import com.springboot.blog.payload.CommentEventDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentLikeEventListener {
    private CommentService commentService;

    @Autowired
    public CommentLikeEventListener(CommentService commentService) {
        this.commentService = commentService;
    }

    @RabbitListener(queues = AppConstants.COMMENT_LIKE_QUEUE)
    public void handleCommentLikeEvent(CommentEventDto event) {
        long commentId = event.getCommentId();
        String currentUsername = event.getCurrentUsername();

        commentService.likeComment(commentId, currentUsername);
    }
}
