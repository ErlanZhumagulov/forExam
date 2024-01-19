package com.example.demo.models;

import com.example.demo.models.enums.Availability;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    private String nameProduct;

    private Integer price;

    private String description;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> imagesProduct;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> categoriesProduct;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AdditionalSettings> optionsProduct;




    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;


    public void addImage(Image img){
        if(imagesProduct == null) imagesProduct = new HashSet<>();
        imagesProduct.add(img);
    }
}
