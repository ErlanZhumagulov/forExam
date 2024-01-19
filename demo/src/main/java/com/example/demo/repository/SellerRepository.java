package com.example.demo.repository;

import com.example.demo.models.Seller;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByUserId(Long userId);
}
