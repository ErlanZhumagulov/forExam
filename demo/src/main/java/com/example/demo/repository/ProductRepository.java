package com.example.demo.repository;

import com.example.demo.models.Product;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByMarketId(Integer id);
}
