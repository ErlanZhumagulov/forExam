package com.example.demo.repository;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByProductId(Integer id);

    Optional<Category> findById(Integer id);

    @Query("SELECT new com.example.demo.dto.CategoryDTO(c.id, c.category, c.product.id) " +
            "FROM Category c")
    List<CategoryDTO> findAllCategories();

    @Query("SELECT new com.example.demo.dto.CategoryDTO(c.id, c.category, c.product.id) " +
            "FROM Category c " +
            "WHERE c.product.id = :productId")
    List<CategoryDTO> findByProductId(Integer productId);
}
