package com.example.demo.repository;

import com.example.demo.dto.AdditionalSettingsDTO;
import com.example.demo.models.AdditionalSettings;
import com.example.demo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdditionalSettingsRepository extends JpaRepository<AdditionalSettings, Integer> {

    List<AdditionalSettings> findAllByProductId(Integer id);

    @Query("SELECT new com.example.demo.dto.AdditionalSettingsDTO(a.id, a.criteria, a.criteriaValue, a.product.id) " +
            "FROM AdditionalSettings a")
    List<AdditionalSettingsDTO> findAllAdditionalSettings();

    @Query("SELECT new com.example.demo.dto.AdditionalSettingsDTO(a.id, a.criteria, a.criteriaValue, a.product.id) " +
            "FROM AdditionalSettings a " +
            "WHERE a.product.id = :productId")
    List<AdditionalSettingsDTO> findByProductId(Integer productId);
    Optional<AdditionalSettings> findById(Integer id);
}
