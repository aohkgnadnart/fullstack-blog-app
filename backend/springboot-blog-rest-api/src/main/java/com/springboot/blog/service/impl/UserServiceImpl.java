package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Tag;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.*;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.UserService;
import com.springboot.blog.utils.SecurityUtils;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    ModelMapper mapper;

    private PasswordEncoder passwordEncoder;

    PostRepository postRepository;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper,
                           PasswordEncoder passwordEncoder,
                           PostRepository postRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
    }

    @Override
    public UserFullResponseDto getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(id)));

        return mapper.map(user, UserFullResponseDto.class);
    }


    @Override
    public List<UserVerySimpleResponseDto> getTop5UsersByUsername(String username) {
        List<User> users = userRepository.findTop5ByUsernameContains(username);
        return users.stream().map(user -> mapper.map(user, UserVerySimpleResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserSimpleResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // update lastVisit of user
        user.setLastVisit(LocalDateTime.now());
        userRepository.save(user);

        return mapper.map(user, UserSimpleResponseDto.class);
    }

    @Override
    public void changePassword(String username, PasswordChangeDto passwordChangeDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), user.getPassword())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        // Mã hóa mật khẩu mới và cập nhật
        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean isFollowing(String currentUsername, Long userId) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

        // Kiểm tra xem currentUser có đang theo dõi targetUser hay không
        return currentUser.getFollowingUsers().contains(targetUser);
    }

    @Override
    public void followUser(String currentUsername, Long userId) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));
        User userToFollow = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

        // Thêm userToFollow vào danh sách following của currentUser
        if (currentUser.getFollowingUsers().add(userToFollow)) {
            userRepository.save(currentUser);
        }
    }

    @Override
    public void unfollowUser(String currentUsername, Long userId) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));
        User userToUnfollow = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

        // Xóa userToUnfollow khỏi danh sách following của currentUser
        if (currentUser.getFollowingUsers().remove(userToUnfollow)) {
            userRepository.save(currentUser);
        }
    }

    @Override
    public boolean isFriend(String currentUsername, Long userId) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));
        User userToCheck = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

        return currentUser.getFriends().contains(userToCheck);
    }

    @Override
    public void sendFriendRequest(String currentUsername, Long userId) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));
        User userToSendRequest = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

        // Nếu đã là bạn bè rồi thì thôi
        if(currentUser.getFriends().contains(userToSendRequest)){
            return ;
        }

        // Nếu có đủ 2 chiều request thì trở thành bạn bè luôn
        if(userToSendRequest.getFriendRequestsSent().contains(currentUser)){
            userToSendRequest.getFriendRequestsSent().remove(currentUser);

            userToSendRequest.getFriends().add(currentUser);
            currentUser.getFriends().add(userToSendRequest);

            userRepository.save(userToSendRequest);
            userRepository.save(currentUser);
            return ;
        }

        if (currentUser.getFriendRequestsSent().add(userToSendRequest)) {
            userRepository.save(currentUser);
        }
    }

    @Override
    public void acceptFriendRequest(String currentUsername, Long userId) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));
        User userToAccept = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

        // Nếu đã là bạn bè rồi thì thôi
        if(currentUser.getFriends().contains(userToAccept)){
            return ;
        }

        if (userToAccept.getFriendRequestsSent().remove(currentUser)) {
            currentUser.getFriends().add(userToAccept);
            userToAccept.getFriends().add(currentUser);
            userRepository.save(currentUser);
            userRepository.save(userToAccept);
        }
    }

    @Override
    public void unfriendUser(String currentUsername, Long userId) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));
        User userToUnfriend = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

        if (currentUser.getFriends().remove(userToUnfriend)) {
            userToUnfriend.getFriends().remove(currentUser);
            userRepository.save(currentUser);
            userRepository.save(userToUnfriend);
        }
    }

    @Override
    public UserPageResponseDto getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> users = userRepository.findAll(pageable);

        // get content for page object
        List<User> listOfUsers = users.getContent();

        List<UserSimpleResponseDto> content = listOfUsers.stream().map(user -> mapper.map(user, UserSimpleResponseDto.class))
                .collect(Collectors.toList());
        UserPageResponseDto userPageResponseDto = new UserPageResponseDto();
        userPageResponseDto.setContent(content);
        userPageResponseDto.setPageNo(pageNo);
        userPageResponseDto.setPageSize(pageSize);
        userPageResponseDto.setTotalElements(users.getTotalElements());
        userPageResponseDto.setTotalPages(users.getTotalPages());
        userPageResponseDto.setLast(users.isLast());

        return userPageResponseDto;
    }

    @Override
    public UserPageResponseDto getFollowerUsers(String username, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> page = userRepository.findFollowerUsers(username, pageable);

        List<UserSimpleResponseDto> content = page.getContent()
                .stream()
                .map(user -> mapper.map(user, UserSimpleResponseDto.class))
                .collect(Collectors.toList());

        return new UserPageResponseDto(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    public UserPageResponseDto getFollowingUsers(String username, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<User> page = userRepository.findFollowingUsers(username, pageable);

        List<UserSimpleResponseDto> content = page.getContent()
                .stream()
                .map(user -> mapper.map(user, UserSimpleResponseDto.class))
                .collect(Collectors.toList());

        return new UserPageResponseDto(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    public UserPageResponseDto getFriends(String username, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<User> page = userRepository.findFriends(username, pageable);

        List<UserSimpleResponseDto> content = page.getContent()
                .stream()
                .map(user -> mapper.map(user, UserSimpleResponseDto.class))
                .collect(Collectors.toList());

        return new UserPageResponseDto(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    public UserPageResponseDto getReceivedFriendRequests(String username, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<User> page = userRepository.findReceivedFriendRequests(username, pageable);

        List<UserSimpleResponseDto> content = page.getContent()
                .stream()
                .map(user -> mapper.map(user, UserSimpleResponseDto.class))
                .collect(Collectors.toList());

        return new UserPageResponseDto(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }


    @Override
    public UserPageResponseDto getSentFriendRequests(String username, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<User> page = userRepository.findSentFriendRequests(username, pageable);

        List<UserSimpleResponseDto> content = page.getContent()
                .stream()
                .map(user -> mapper.map(user, UserSimpleResponseDto.class))
                .collect(Collectors.toList());

        return new UserPageResponseDto(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    public void upgradeToAdmin(long userId) {
        userRepository.upgradeToAdmin(userId);
    }


    @Override
    public void downgradeFromAdmin(String currentUsername) {
        long userId = userRepository.findUserIdByUsername(currentUsername);
        userRepository.downgradeFromAdmin(userId);
    }

    @Override
    public UserPageResponseDto getUsersWhoLikedPost(long postId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> page = postRepository.findUsersWhoLikedPost(postId, pageable);

        List<UserSimpleResponseDto> content = page.getContent()
                .stream()
                .map(user -> mapper.map(user, UserSimpleResponseDto.class))
                .collect(Collectors.toList());

        return new UserPageResponseDto(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

}
