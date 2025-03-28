package com.example.demo.service;


import com.example.demo.config.ApplicationConfig;
import com.example.demo.dto.UserDetailsResponseDto;
import com.example.demo.jwt.service.JwtService;
import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;

    private User findUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    return new Exception("User not found.");
                });
    }

    public ResponseEntity<Object> getUserById(Long id, String token) {
        try {
            String email = jwtService.extractUserEmailFromToken(token);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> {
                        return new Exception("User not found.");
                    });

            UserDetailsResponseDto userDetailsResponseDto = UserDetailsResponseDto.builder()
                    .userName(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();

            return ResponseEntity.ok(userDetailsResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
