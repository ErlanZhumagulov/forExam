package com.example.demo.models;

import lombok.Data;

@Data
public class  ProductScore {

    public ProductScore(Product product, int score) {
        this.product = product;
        this.score = score;
    }

    private Product product;
    private int score;

    public Product getProduct() {
        return product;
    }

    public int getScore() {
        return score;
    }
}
