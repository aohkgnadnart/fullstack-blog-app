package com.springboot.blog.controller;

import com.springboot.blog.payload.JWTAuthResponseDto;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build login REST API
    @PostMapping(value = {"/login", "signin"})
    public ResponseEntity<JWTAuthResponseDto> login(@Valid  @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponseDto jwtAuthResponseDto = new JWTAuthResponseDto();
        jwtAuthResponseDto.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponseDto, HttpStatus.OK);
    }

    // Bulid register REST API
    @PostMapping(value = {"/register", "signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
