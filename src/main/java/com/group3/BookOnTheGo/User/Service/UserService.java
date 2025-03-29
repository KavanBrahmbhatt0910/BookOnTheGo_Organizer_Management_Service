package com.group3.BookOnTheGo.User.Service;

import com.group3.BookOnTheGo.Exception.MetaBlogException;
import com.group3.BookOnTheGo.Image.Model.Image;
import com.group3.BookOnTheGo.Image.Service.ImageService;
import com.group3.BookOnTheGo.Jwt.ServiceLayer.JwtService;
import com.group3.BookOnTheGo.User.DataTransferObject.SavedBlogResponseDto;
import com.group3.BookOnTheGo.User.DataTransferObject.UserDetailsResponseDto;
import com.group3.BookOnTheGo.User.DataTransferObject.UserUpdateRequestDto;
import com.group3.BookOnTheGo.User.Model.User;
import com.group3.BookOnTheGo.User.Repository.IUserRepository;
import com.group3.BookOnTheGo.Utils.MetaBlogResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final ImageService imageService;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new MetaBlogException("User not found.");
                });
    }

    @Override
    public ResponseEntity<Object> getUserById(Long id, String token) {
        try {
            String email = jwtService.extractUserEmailFromToken(token);
            logger.info("Fetching user details for ID: {} with email: {}", id, email);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("User not found with ID: {}", id);
                        return new MetaBlogException("User not found.");
                    });

            UserDetailsResponseDto userDetailsResponseDto = UserDetailsResponseDto.builder()
                    .userName(user.getUsername())
                    .imageURL(user.getImageURL())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .linkedinURL(user.getLinkedinURL())
                    .githubURL(user.getGithubURL())
                    .bio(user.getBio())
                    .build();

            return ResponseEntity.ok().body(MetaBlogResponse.builder()
                    .success(true)
                    .message("User details fetched successfully.")
                    .data(userDetailsResponseDto)
                    .build());
        } catch (MetaBlogException e) {
            logger.error("Error fetching user details for ID: {}", id);
            logger.error("Message of the error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity<Object> getUserDetails(String token) {
        try {
            String email = jwtService.extractUserEmailFromToken(token);
            logger.info("Fetching user details for email: {}", email);
            User user = findUserByEmail(email);

            UserDetailsResponseDto userDetailsResponseDto = UserDetailsResponseDto.builder()
                    .userName(user.getUsername())
                    .imageURL(user.getImageURL())
                    .email(user.getEmail())
                    .linkedinURL(user.getLinkedinURL())
                    .githubURL(user.getGithubURL())
                    .bio(user.getBio())
                    .build();

            return ResponseEntity.ok().body(MetaBlogResponse.builder()
                    .success(true)
                    .message("User details fetched successfully.")
                    .data(userDetailsResponseDto)
                    .build());
        } catch (MetaBlogException e) {
            logger.error("Error fetching user details");
            logger.error("Message of the error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity<Object> updateUserDetails(UserUpdateRequestDto request, String token) {
        try {
            String email = jwtService.extractUserEmailFromToken(token);
            logger.info("Updating user details for email: {}", email);
            User user = findUserByEmail(email);

            if(request.getImageURL() != null && request.getImageURL().isPresent()) {
                Optional<MultipartFile> file = request.getImageURL();
                Image image = imageService.uploadImage(file.get());
                user.setImageURL(image.getUrl());
            }
            user.setUsername(request.getUserName());
            user.setBio(request.getBio());
            user.setGithubURL(request.getGithubURL());
            user.setLinkedinURL(request.getLinkedinURL());

            userRepository.save(user);
            return ResponseEntity.ok().body(MetaBlogResponse.builder()
                    .success(true)
                    .message("User details updated successfully.")
                    .build());
        } catch (Exception e) {
            logger.error("Error updating user details");
            logger.error("Message of the error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(MetaBlogResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }
}
