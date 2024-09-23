package com.springboot.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank
    @Size(max = 320)
    private String usernameOrEmail;

    @NotEmpty
    @Size(max = 64)
    private String password;
}
