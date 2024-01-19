package com.example.demo.models;

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
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ECategory category;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
