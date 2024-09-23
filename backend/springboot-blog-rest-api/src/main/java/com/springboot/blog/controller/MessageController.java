package com.springboot.blog.controller;

import com.springboot.blog.payload.MessagePageResponseDto;
import com.springboot.blog.payload.MessageRequestDto;
import com.springboot.blog.service.MessageService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/send/{userId}")
    public ResponseEntity<String> sendMessage(
            @PathVariable long userId,
            Authentication authentication,
            @Valid @RequestBody MessageRequestDto messageRequestDto) {

        String currentUsername = authentication.getName();
        messageService.sendMessage(userId, currentUsername, messageRequestDto);
        return new ResponseEntity<>("Message sent successfully!", HttpStatus.CREATED);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/history/{userId}")
    public ResponseEntity<MessagePageResponseDto> getMessages(
            Authentication authentication,
            @PathVariable long userId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_MESSAGE_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_MESSAGE_PAGE_SIZE, required = false) int pageSize) {

        String currentUsername = authentication.getName();
        MessagePageResponseDto messages = messageService.getMessages(userId, currentUsername, pageNo, pageSize);
        return ResponseEntity.ok(messages);
    }

}
