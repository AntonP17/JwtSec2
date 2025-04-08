package com.example.jwtsecurity2.repository;

import com.example.jwtsecurity2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
