package com.example.demo.repository;

import com.example.demo.models.Market;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Integer> {
    List<Market> findAllBySellerId(Integer sellerId);

}
