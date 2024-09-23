package com.springboot.blog.listener;

import com.springboot.blog.payload.CommentEventDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentNoLikeEventListener {
    private CommentService commentService;

    @Autowired
    public CommentNoLikeEventListener(CommentService commentService) {
        this.commentService = commentService;
    }

    @RabbitListener(queues = AppConstants.COMMENT_NO_LIKE_QUEUE)
    public void handleCommentNoLikeEvent(CommentEventDto event) {
        long commentId = event.getCommentId();
        String currentUsername = event.getCurrentUsername();

        commentService.noLikeComment(commentId, currentUsername);
    }
}
