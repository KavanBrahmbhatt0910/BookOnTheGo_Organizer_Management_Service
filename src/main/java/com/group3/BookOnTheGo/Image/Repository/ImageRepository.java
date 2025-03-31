package com.group3.BookOnTheGo.Image.Repository;

import com.group3.BookOnTheGo.Image.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String imageURL);
}
