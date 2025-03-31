package com.group3.BookOnTheGo.Image.Controller;

import com.group3.BookOnTheGo.Exception.BookOnTheGoException;
import com.group3.BookOnTheGo.Image.Model.Image;
import com.group3.BookOnTheGo.Image.Service.IImageService;
import com.group3.BookOnTheGo.Utils.BookOnTheGoResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.mail.MessagingException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload-profile-image")
    public ResponseEntity<Object> uploadProfileImage(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token) {
        try {
            Image image = imageService.uploadImage(file);
            if (image == null)
                return ResponseEntity.badRequest().body(BookOnTheGoResponse.builder()
                        .success(false)
                        .message("Image not uploaded")
                        .build());
            return imageService.setUserUrl(image.getUrl(), token);
        } catch (IllegalArgumentException | BookOnTheGoException | MessagingException e) {
            return ResponseEntity.badRequest().body(BookOnTheGoResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("/get-profile-image")
    public ResponseEntity<Object> getImage(@RequestHeader("Authorization") String token) {
        try {
            return imageService.getProfileImage(token);
        } catch (IllegalArgumentException | BookOnTheGoException e) {
            return ResponseEntity.badRequest().body(BookOnTheGoResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }
}
