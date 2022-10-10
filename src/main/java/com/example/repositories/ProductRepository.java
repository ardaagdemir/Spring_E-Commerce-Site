package com.example.repositories;

import com.example.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Object> {

    Optional<Product> findByTitleEqualsIgnoreCase(String title);

}
