package com.group3.BookOnTheGo.User.Controller;

import com.group3.BookOnTheGo.User.DataTransferObject.UserUpdateRequestDto;
import com.group3.BookOnTheGo.User.Service.IUserService;
import com.group3.BookOnTheGo.Utils.MetaBlogResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            logger.error("Token not provided");
            return ResponseEntity.status(401).body(MetaBlogResponse.builder()
                    .success(false)
                    .message("Token not provided")
                    .build());
        }

        logger.info("Fetching user details for ID: {} with token: {}", id, token);
        try {
            return userService.getUserById(id, token.replace("Bearer ", ""));
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching user details for ID: {}", id);
            logger.error("Message of the error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getUserDetails(@RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            logger.error("Token not provided");
            return ResponseEntity.status(401).body(MetaBlogResponse.builder()
                    .success(false)
                    .message("Token not provided")
                    .build());
        }

        logger.info("Fetching user details with token: {}", token);
        try {
            return userService.getUserDetails(token.split(" ")[1].trim());
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching user details");
            logger.error("Message of the error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUserDetails(@ModelAttribute UserUpdateRequestDto request, @RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            logger.error("Token not provided");
            return ResponseEntity.status(401).body(MetaBlogResponse.builder()
                    .success(false)
                    .message("Token not provided")
                    .build());
        }

        logger.info("Updating user details with token: {}", token);
        try {
            return userService.updateUserDetails(request, token.split(" ")[1].trim());
        } catch (IllegalArgumentException e) {
            logger.error("Error updating user details");
            logger.error("Message of the error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }
}
