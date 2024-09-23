package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Message;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.*;
import com.springboot.blog.repository.MessageRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private UserRepository userRepository;

    private ModelMapper mapper;

    public MessageServiceImpl(MessageRepository messageRepository,
                              UserRepository userRepository,
                              ModelMapper mapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void sendMessage(long recipientId, String senderUsername, MessageRequestDto messageRequestDto) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", senderUsername));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(recipientId)));

        Message message = new Message();
        message.setContent(messageRequestDto.getContent());
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setCreateAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        MessageRequestDto newMessageRequestDto = mapper.map(savedMessage, MessageRequestDto.class);

    }


    @Override
    public MessagePageResponseDto getMessages(long userId, String currentUsername, int pageNo, int pageSize) {
        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        long curUserId = userRepository.findUserIdByUsername(currentUsername);

        Page<Message> messages = messageRepository.findMessagesBetweenUsers(userId, curUserId, pageable);

        // get content for page object
        List<Message> listOfMessages = messages.getContent();

        List<MessageResponseDto> content = listOfMessages.stream().map(message -> mapper.map(message, MessageResponseDto.class)).collect(Collectors.toList());
        MessagePageResponseDto messagePageResponseDto = new MessagePageResponseDto();
        messagePageResponseDto.setContent(content);
        messagePageResponseDto.setPageNo(pageNo);
        messagePageResponseDto.setPageSize(pageSize);
        messagePageResponseDto.setTotalElements(messages.getTotalElements());
        messagePageResponseDto.setTotalPages(messages.getTotalPages());
        messagePageResponseDto.setLast(messages.isLast());

        return messagePageResponseDto;
    }

}
