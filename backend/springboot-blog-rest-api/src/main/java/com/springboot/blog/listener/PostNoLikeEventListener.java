package com.springboot.blog.listener;

import com.springboot.blog.payload.PostEventDto;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostNoLikeEventListener {
    private PostService postService;

    @Autowired
    public PostNoLikeEventListener(PostService postService) {
        this.postService = postService;
    }

    @RabbitListener(queues = AppConstants.POST_NO_LIKE_QUEUE)
    public void handlePostNoLikeEvent(PostEventDto event) {
        long postId = event.getPostId();
        String currentUsername = event.getCurrentUsername();

        postService.noLikePost(postId, currentUsername);
    }
}
