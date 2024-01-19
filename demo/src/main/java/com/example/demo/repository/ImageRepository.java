package com.example.demo.repository;

import com.example.demo.dto.ImageDTO;
import com.example.demo.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findAllByProductId(Integer id);

    @Query("SELECT new com.example.demo.dto.ImageDTO(i.id, i.product) " +
            "FROM Image i " +
            "WHERE i.product.id = :productId")
    List<ImageDTO> findByProductId(Integer productId);
    Optional<Image> findById(Integer id);
}
