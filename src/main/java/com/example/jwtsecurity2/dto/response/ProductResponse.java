package com.example.jwtsecurity2.dto.response;

import com.example.jwtsecurity2.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<Product> products;
}
