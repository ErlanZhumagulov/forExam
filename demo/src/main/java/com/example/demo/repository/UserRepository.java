package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    List<User> findAllByEmail(String email);
}

//spring.jpa.hibernate.ddl-auto=create
//spring.jpa.hibernate.show-sql= true