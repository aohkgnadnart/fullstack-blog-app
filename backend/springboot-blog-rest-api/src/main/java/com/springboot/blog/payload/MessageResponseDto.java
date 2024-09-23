package com.springboot.blog.payload;

import com.springboot.blog.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {

    private Long id;

    private String content;

    private UserVerySimpleResponseDto sender;

    private UserVerySimpleResponseDto recipient;

    private LocalDateTime createAt;
}
