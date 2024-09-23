package com.springboot.blog.listener;

import com.springboot.blog.payload.PostEventDto;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteCommentOnPostEventListener {
    private PostService postService;

    @Autowired
    public DeleteCommentOnPostEventListener(PostService postService) {
        this.postService = postService;
    }

    @RabbitListener(queues = AppConstants.DELETE_COMMENT_ON_POST_QUEUE)
    public void handleDeleteCommentOnPostEvent(PostEventDto event) {
        long postId = event.getPostId();

        postService.deleteCommentOnPost(postId);
    }
}
