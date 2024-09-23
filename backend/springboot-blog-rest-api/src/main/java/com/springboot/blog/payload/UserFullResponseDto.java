package com.springboot.blog.payload;

import com.springboot.blog.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class  UserFullResponseDto {

    private Long id;

    private String name;

    private String username;

    private String email;

    private LocalDateTime createAt;

    private LocalDateTime lastVisit;

    private List<RoleSimpleResponseDto> roles;

}
