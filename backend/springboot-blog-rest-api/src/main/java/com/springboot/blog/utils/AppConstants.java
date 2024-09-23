package com.springboot.blog.utils;

public class AppConstants {
    public static final String DEFAULT_POST_PAGE_NUMBER = "0";
    public static final String DEFAULT_POST_PAGE_SIZE = "10";
    public static final String DEFAULT_POST_SORT_BY = "lastUpdated";
    public static final String DEFAULT_POST_SORT_DIR = "desc";

    public static final String DEFAULT_USER_PAGE_NUMBER = "0";
    public static final String DEFAULT_USER_PAGE_SIZE = "10";
    public static final String DEFAULT_USER_SORT_BY = "username";
    public static final String DEFAULT_USER_SORT_DIR = "asc";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public static final String POST_VIEW_QUEUE = "postViewQueue";

    public static final String POST_LIKE_QUEUE = "postLikeQueue";
    public static final String POST_DISLIKE_QUEUE = "postDislikeQueue";

    public static final String POST_NO_LIKE_QUEUE = "postNoLikeQueue";
    public static final String POST_NO_DISLIKE_QUEUE = "postNoDislikeQueue";

    public static final String COMMENT_ON_POST_QUEUE = "commentOnPostQueue";
    public static final String DELETE_COMMENT_ON_POST_QUEUE = "deleteCommentOnPostQueue";

    public static final String COMMENT_LIKE_QUEUE = "commentLikeQueue";
    public static final String COMMENT_DISLIKE_QUEUE = "commentDislikeQueue";

    public static final String COMMENT_NO_LIKE_QUEUE = "commentNoLikeQueue";
    public static final String COMMENT_NO_DISLIKE_QUEUE = "commentNoDislikeQueue";

    public static final String MESSAGE_QUEUE = "message_queue";
    public static final String MESSAGE_EXCHANGE = "message_exchange";
    public static final String ROUTING_KEY = "message_routing_key";
    public static final String DEFAULT_MESSAGE_PAGE_NUMBER = "0";
    public static final String DEFAULT_MESSAGE_PAGE_SIZE = "10";
}
