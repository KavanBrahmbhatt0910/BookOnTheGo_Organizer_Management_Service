package com.example.demo.service;

import com.example.demo.config.ApplicationConfig;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.dto.RegisterResponseDto;
import com.example.demo.jwt.service.JwtService;
import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.ResetPasswordRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class AuthenticationService {
    private final IUserRepository IUserRepository;
    private final JwtService jwtService;
    private final ApplicationConfig applicationConfig;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<Object> register(RegisterRequestDto request) {
        try {
            Optional<User> existingUser = IUserRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            var user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(applicationConfig.passwordEncoder().encode(request.getPassword()))
                    .registerAt((double) (System.currentTimeMillis()))
                    .lastLoginTime((double) (System.currentTimeMillis()))
                    .build();

            IUserRepository.save(user);
            String accessToken = jwtService.generateJwtToken(user);
            String refreshToken = jwtService.generateRefreshToken( user);

            user.setRefreshToken(refreshToken);
            user.setAccessToken(accessToken);

            IUserRepository.save(user);

            return ResponseEntity.ok(RegisterResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .role("User")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> forgetPassword(String email) {
        try {
            Optional<User> existingUser = IUserRepository.findByEmail(email);
            if (existingUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with this email.");
            }
//            int otp = otpService.generateOTP();
//            User currentUser = existingUser.get();
//            Long id = currentUser.getId();
//            otpService.registerOTP(otp, id);
//            try {
//                emailService.sendVerificationOTP(email, otp);
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email.";
//            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> resetPassword(ResetPasswordRequestDto request) {
        try {

            Optional<User> userOptional = IUserRepository.findByEmail(request.getEmail());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with this email.");
            }
            User user = userOptional.get();
            user.setPassword(applicationConfig.passwordEncoder().encode(request.getNewPassword()));
            IUserRepository.save(user);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @Override
//    public ResponseEntity<Object> findUser(String email) {
//        try {
//            logger.info("Finding user with email: {}", email);
//            Optional<User> existingUser = IUserRepository.findByEmail(email);
//            if (existingUser.isEmpty()) {
//                logger.error("User does not exist with this email");
//                return new ResponseEntity<>(MetaBlogResponse.builder()
//                        .success(false)
//                        .message("User does not exist with this email.")
//                        .build(), HttpStatus.NOT_FOUND);
//            }
//            logger.info("User found with this email");
//            return new ResponseEntity<>(MetaBlogResponse.builder()
//                    .success(true)
//                    .message("A user with this email exists.")
//                    .build(), HttpStatus.OK);
//        } catch (MetaBlogException e) {
//            return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
//                    .success(false)
//                    .message(e.getMessage())
//                    .build());
//        }
//    }

    public ResponseEntity<Object> login(LoginRequestDto request) {
        try {
            User user = IUserRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new Exception("User not found"));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            String accessToken = jwtService.generateJwtToken(user);
            String refreshToken = jwtService.generateRefreshToken( user);

            user.setLastLoginTime((double) System.currentTimeMillis());
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            IUserRepository.save(user);

            return ResponseEntity.ok(LoginResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .role("User")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
