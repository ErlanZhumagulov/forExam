package com.example.demo.dto;

import com.example.demo.models.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ImageDTO {

    private Integer id;

    private Product product;

}
