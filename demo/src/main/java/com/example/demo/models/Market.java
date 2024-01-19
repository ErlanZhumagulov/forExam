package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "market")
public class Market {
    @Id
    @GeneratedValue
    private Integer id;

    private String marketName;

    private String marketDescription;

    private String address;

    private Double xCoordinate;
    private Double yCoordinate;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;


    // Определение связи сущности "Магазин" с сущностью "Продавец"


    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private Set<Product> products;
    // Другие поля и методы
}
