package com.example.demo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterResponseDto<T> {
    private String accessToken;
    private String refreshToken;
    private String role;
}

