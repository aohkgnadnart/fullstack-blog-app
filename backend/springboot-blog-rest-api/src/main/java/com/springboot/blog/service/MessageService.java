package com.springboot.blog.service;

import com.springboot.blog.payload.MessagePageResponseDto;
import com.springboot.blog.payload.MessageRequestDto;

import java.util.List;

public interface MessageService {

    void sendMessage(long recipientId, String senderUsername, MessageRequestDto messageRequestDto);

    MessagePageResponseDto getMessages(long userId, String currentUsername, int pageNo, int pageSize);
}
