package com.example.demo.dto;


import com.example.demo.models.Product;
import com.example.demo.models.enums.ECategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {


    private Integer id;

    @Enumerated(EnumType.STRING)
    private ECategory category;

    private Integer productId;

}
