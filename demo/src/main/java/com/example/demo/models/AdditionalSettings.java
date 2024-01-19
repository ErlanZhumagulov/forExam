package com.example.demo.models;

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
@Table(name = "additional_settings")
public class AdditionalSettings {

    @Id
    @GeneratedValue
    private Integer id;

    private String criteria;

    private String criteriaValue;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
