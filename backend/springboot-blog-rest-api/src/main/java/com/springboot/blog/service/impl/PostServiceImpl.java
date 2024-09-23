package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Tag;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.*;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.TagRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.TagService;
import com.springboot.blog.utils.AppConstants;
import com.springboot.blog.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private UserRepository userRepository;

    private TagRepository tagRepository;

    private ModelMapper mapper;

    private RabbitTemplate rabbitTemplate;

    private TagService tagService;

    private CommentRepository commentRepository;


    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository,
                           TagRepository tagRepository,
                           ModelMapper mapper,
                           RabbitTemplate rabbitTemplate,
                           TagService tagService,
                           CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
        this.tagService = tagService;
        this.commentRepository = commentRepository;
    }

    @Override
    public PostFullResponseDto createPost(PostRequestDto postRequestDto) {

        // Kiểm tra tiêu đề đã tồn tại
        if (postRepository.existsByTitle(postRequestDto.getTitle())) {
            throw new BlogAPIException(HttpStatus.CONFLICT, "Title already exists: " + postRequestDto.getTitle());
        }

        // kiem tra xem co it nhat 1 tag khong
        if(postRequestDto.getTags() == null || postRequestDto.getTags().isEmpty()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Post must have at least one tag");
        }

        // kiem tra xem co nhieu nhat 5 tags
        if(postRequestDto.getTags().size() > 5){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Post cannot have more than 5 tags");
        }

//        Set<Tag> tags = new HashSet<>();
        List<Tag> tags = new ArrayList<>();
        for (TagRequestDto tagRequestDto : postRequestDto.getTags()) {
            tagRequestDto.setName(tagRequestDto.getName().toLowerCase()); // Chuyển thành chữ thường trước khi kiểm tra
            Tag tag = tagRepository.findByName(tagRequestDto.getName())
                    .orElseGet(() -> {
                        return mapper.map(tagService.createTag(tagRequestDto), Tag.class);
                    });
            tags.add(tag);
        }

        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Post post = mapper.map(postRequestDto, Post.class);
        post.setTags(tags);
        post.setUser(user);
        post.setLastUpdated(LocalDateTime.now());
        Post newPost = postRepository.save(post);

        return mapper.map(newPost, PostFullResponseDto.class);
    }

    @Override
    public PostPageResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostSimpleResponseDto> content = listOfPosts.stream().map(post -> mapper.map(post, PostSimpleResponseDto.class)).collect(Collectors.toList());
        PostPageResponseDto postPageResponseDto = new PostPageResponseDto();
        postPageResponseDto.setContent(content);
        postPageResponseDto.setPageNo(pageNo);
        postPageResponseDto.setPageSize(pageSize);
        postPageResponseDto.setTotalElements(posts.getTotalElements());
        postPageResponseDto.setTotalPages(posts.getTotalPages());
        postPageResponseDto.setLast(posts.isLast());

        return postPageResponseDto;
    }

    @Override
    public PostPageResponseDto getPostsByTitle(String title, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findByTitleContaining(title, pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostSimpleResponseDto> content = listOfPosts.stream().map(post -> mapper.map(post, PostSimpleResponseDto.class)).collect(Collectors.toList());
        PostPageResponseDto postPageResponseDto = new PostPageResponseDto();
        postPageResponseDto.setContent(content);
        postPageResponseDto.setPageNo(pageNo);
        postPageResponseDto.setPageSize(pageSize);
        postPageResponseDto.setTotalElements(posts.getTotalElements());
        postPageResponseDto.setTotalPages(posts.getTotalPages());
        postPageResponseDto.setLast(posts.isLast());

        return postPageResponseDto;
    }

    @Override
    public PostPageResponseDto getPostsByTagId(long tagId, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findByTagId(tagId, pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostSimpleResponseDto> content = listOfPosts.stream().map(post -> mapper.map(post, PostSimpleResponseDto.class)).collect(Collectors.toList());
        PostPageResponseDto postPageResponseDto = new PostPageResponseDto();
        postPageResponseDto.setContent(content);
        postPageResponseDto.setPageNo(pageNo);
        postPageResponseDto.setPageSize(pageSize);
        postPageResponseDto.setTotalElements(posts.getTotalElements());
        postPageResponseDto.setTotalPages(posts.getTotalPages());
        postPageResponseDto.setLast(posts.isLast());

        return postPageResponseDto;
    }

    @Override
    public PostPageResponseDto getPostsByUserId(long userId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findByUserId(userId, pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostSimpleResponseDto> content = listOfPosts.stream().map(post -> mapper.map(post, PostSimpleResponseDto.class)).collect(Collectors.toList());
        PostPageResponseDto postPageResponseDto = new PostPageResponseDto();
        postPageResponseDto.setContent(content);
        postPageResponseDto.setPageNo(pageNo);
        postPageResponseDto.setPageSize(pageSize);
        postPageResponseDto.setTotalElements(posts.getTotalElements());
        postPageResponseDto.setTotalPages(posts.getTotalPages());
        postPageResponseDto.setLast(posts.isLast());

        return postPageResponseDto;
    }

    @Override
    public PostFullResponseDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
        return mapper.map(post, PostFullResponseDto.class);
    }

    @Override
    public PostFullResponseDto updatePost(PostRequestDto postRequestDto, long id) {
        // get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        // Kiểm tra quyền sở hữu
        if (!post.getUser().getUsername().equals(SecurityUtils.getCurrentUsername())) {
            throw new BlogAPIException(HttpStatus.FORBIDDEN, "You do not have permission to edit this post");
        }

        // Kiểm tra tiêu đề đã tồn tại
        if(!postRequestDto.getTitle().equals(post.getTitle())){
            if (postRepository.existsByTitle(postRequestDto.getTitle())) {
                throw new BlogAPIException(HttpStatus.CONFLICT, "Title already exists: " + postRequestDto.getTitle());
            }
        }

        // kiem tra xem co it nhat 1 tag khong
        if(postRequestDto.getTags() == null || postRequestDto.getTags().isEmpty()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Post must have at least one tag");
        }

        // kiem tra xem co nhieu nhat 5 tags
        if(postRequestDto.getTags().size() > 5){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Post cannot have more than 5 tags");
        }

        List<Tag> tags = new ArrayList<>();
        for (TagRequestDto tagRequestDto : postRequestDto.getTags()) {
            String name = tagRequestDto.getName().toLowerCase(); // Chuyển thành chữ thường trước khi kiểm tra
            Tag tag = tagRepository.findByName(name)
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.setName(name);
                        return tagRepository.save(newTag);
                    });
            tags.add(tag);
        }

        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        post.setTags(tags);
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setLastUpdated(LocalDateTime.now());
        Post newPost = postRepository.save(post);

        return mapper.map(newPost, PostFullResponseDto.class);
    }





    @Override
    public void deletePostById(long id) {

        // get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        // kiem tra xem role cua currentUsernam co phai admin hoac author khong
        if(!SecurityUtils.hasRole(AppConstants.ROLE_ADMIN) && !SecurityUtils.getCurrentUsername().equals(post.getUser().getUsername())){
            throw new BlogAPIException(HttpStatus.FORBIDDEN, "You do not have permission to delete this post");
        }

        postRepository.delete(post);
    }

    @Override
    public PostPageResponseDto getPostsFromFollowingUsers(String currentUsername, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));
        List<User> followingUsers = currentUser.getFollowingUsers().stream().collect(Collectors.toList());

        Page<Post> posts = postRepository.findByUserIn(followingUsers, pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostSimpleResponseDto> content = listOfPosts.stream().map(post -> mapper.map(post, PostSimpleResponseDto.class)).collect(Collectors.toList());
        PostPageResponseDto postPageResponseDto = new PostPageResponseDto();
        postPageResponseDto.setContent(content);
        postPageResponseDto.setPageNo(pageNo);
        postPageResponseDto.setPageSize(pageSize);
        postPageResponseDto.setTotalElements(posts.getTotalElements());
        postPageResponseDto.setTotalPages(posts.getTotalPages());
        postPageResponseDto.setLast(posts.isLast());

        return postPageResponseDto;
    }

    @Override
    public boolean hasLike(long postId, String currentUsername) {
        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }
        return postRepository.hasUserLikedPost(postId, userId);
    }

    @Override
    public boolean hasDislike(long postId, String currentUsername) {
        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }
        return postRepository.hasUserDislikedPost(postId, userId);
    }

    @Override
    @Transactional
    public void dislikePost(long postId, String currentUsername) {

        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }

        if(!postRepository.hasUserDislikedPost(postId, userId)){
            postRepository.addDislike(postId, userId);
            postRepository.incrementDislikeCount(postId);

            if(postRepository.hasUserLikedPost(postId, userId)){
                postRepository.deleteLike(postId, userId);
                postRepository.decrementLikeCount(postId);
            }
        }
    }

    @Override
    @Transactional
    public void likePost(long postId, String currentUsername) {

        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }

        if(!postRepository.hasUserLikedPost(postId, userId)){
            postRepository.addLike(postId, userId);
            postRepository.incrementLikeCount(postId);

            if(postRepository.hasUserDislikedPost(postId, userId)){
                postRepository.deleteDislike(postId, userId);
                postRepository.decrementDislikeCount(postId);
            }
        }
    }

    @Override
    @Transactional
    public void noDislikePost(long postId, String currentUsername) {

        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }

        if(postRepository.hasUserDislikedPost(postId, userId)){
            postRepository.deleteDislike(postId, userId);
            postRepository.decrementDislikeCount(postId);
        }
    }

    @Override
    @Transactional
    public void noLikePost(long postId, String currentUsername) {

        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }

        if(postRepository.hasUserLikedPost(postId, userId)){
            postRepository.deleteLike(postId, userId);
            postRepository.decrementLikeCount(postId);
        }
    }

    @Override
    @Transactional
    public void addViewer(long postId, String currentUsername) {

        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }

        if(!postRepository.hasUserViewedPost(postId, userId)){
            postRepository.addViewer(postId, userId);
            postRepository.incrementViewerCount(postId);
        }
    }

    @Override
    public void userLikePost(long postId, String currentUsername) {
        PostEventDto event = new PostEventDto(postId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.POST_LIKE_QUEUE, event);
    }

    @Override
    public void userNoLikePost(long postId, String currentUsername) {
        PostEventDto event = new PostEventDto(postId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.POST_NO_LIKE_QUEUE, event);
    }

    @Override
    public void userDislikePost(long postId, String currentUsername) {
        PostEventDto event = new PostEventDto(postId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.POST_DISLIKE_QUEUE, event);
    }

    @Override
    public void userNoDislikePost(long postId, String currentUsername) {
        PostEventDto event = new PostEventDto(postId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.POST_NO_DISLIKE_QUEUE, event);
    }

    @Override
    public void userViewPost(long postId, String currentUsername) {
        PostEventDto event = new PostEventDto(postId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.POST_VIEW_QUEUE, event);
    }

    @Override
    @Transactional
    public void commentOnPost(long postId) {
        postRepository.incrementCommentCount(postId);
    }

    @Override
    public void userCommentOnPost(long postId) {
        PostEventDto event = new PostEventDto(postId, "");
        rabbitTemplate.convertAndSend(AppConstants.COMMENT_ON_POST_QUEUE, event);
    }

    @Override
    @Transactional
    public void deleteCommentOnPost(long postId) {
        long currentCommentCount = commentRepository.countByPostId(postId);
        postRepository.decrementCommentCount(postId, currentCommentCount);
    }

    @Override
    public void userDeleteCommentOnPost(long postId) {
        PostEventDto event = new PostEventDto(postId, "");
        rabbitTemplate.convertAndSend(AppConstants.DELETE_COMMENT_ON_POST_QUEUE, event);
    }



//    private PostPersonalizedResponseDto mapToDto(Post post){
//        PostPersonalizedResponseDto postPersonalizedResponseDto = mapper.map(post, PostPersonalizedResponseDto.class);
//
//        postPersonalizedResponseDto.setComments(post.getComments().stream()
//                .map(comment -> mapToDto(comment))
//                .collect(Collectors.toList()));
//
//        postPersonalizedResponseDto.setComments(CommentUtils.convertToCommentTreeV2(postPersonalizedResponseDto.getComments()));
//
//        postPersonalizedResponseDto.setHasLike(hasLike(post.getId(), SecurityUtils.getCurrentUsername()));
//        postPersonalizedResponseDto.setHasDislike(hasDislike(post.getId(), SecurityUtils.getCurrentUsername()));
//        return postPersonalizedResponseDto;
//    }
//
//    private CommentPersonalizedResponseDto mapToDto(Comment comment){
//        CommentPersonalizedResponseDto commentPersonalizedResponseDto = mapper.map(comment, CommentPersonalizedResponseDto.class);
//
//        commentPersonalizedResponseDto.setHasLike(commentService.hasLike(comment.getId(), SecurityUtils.getCurrentUsername()));
//        commentPersonalizedResponseDto.setHasDislike(commentService.hasDislike(comment.getId(), SecurityUtils.getCurrentUsername()));
//
//        return commentPersonalizedResponseDto;
//    }
}
