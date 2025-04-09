package com.example.jwtsecurity2.repository;

import com.example.jwtsecurity2.dto.response.ProductProjection;
import com.example.jwtsecurity2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<ProductProjection> findAllProjectedBy();
}
