package com.example.demo.controller;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.dto.ResetPasswordRequestDto;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService service) {
        this.authenticationService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@NotNull @RequestBody RegisterRequestDto request) {
        try {
            return authenticationService.register(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<Object> forgetPassword(@NotNull @RequestParam String email) {
        try {
            return authenticationService.forgetPassword(email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@NotNull @RequestBody ResetPasswordRequestDto request) {
        try {
            return authenticationService.resetPassword(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/get-user")
//    public ResponseEntity<Object> getUser(@NotNull @RequestParam String email) {
//        try {
//            if (email.isEmpty()) {
//                return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
//                        .success(false)
//                        .message("Email is empty")
//                        .build());
//            }
//            return authenticationService.findUser(email);
//        } catch (MetaBlogException e) {
//            logger.error("Error getting user with email: {}", email);
//            logger.error("Message of the error: {}", e.getMessage());
//            return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
//                    .success(false)
//                    .message(e.getMessage())
//                    .build());
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@NotNull @RequestBody LoginRequestDto request) {
        try {
            return authenticationService.login(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
