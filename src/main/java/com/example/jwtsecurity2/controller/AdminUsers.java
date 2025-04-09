package com.example.jwtsecurity2.controller;



import com.example.jwtsecurity2.dto.request.ProductRequest;
import com.example.jwtsecurity2.dto.response.ProductProjection;
import com.example.jwtsecurity2.entity.Product;
import com.example.jwtsecurity2.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminUsers {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/public/product")
    public List<ProductProjection> getAllProducts(){
        return productRepo.findAllProjectedBy();
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderator/saveproduct")
    public ResponseEntity<Object> saveProduct(@RequestBody ProductRequest productRequest){
        Product productToSave = new Product();
        productToSave.setName(productRequest.getName());
        return ResponseEntity.ok(productRepo.save(productToSave));
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone(){
        return ResponseEntity.ok("USers alone can access this ApI only");
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/superadmin/both")
    public ResponseEntity<Object> bothAdminaAndUsersApi(){
        return ResponseEntity.ok("Both Admin and Users Can  access the api");
    }


    @GetMapping("/public/email")
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getName());
        return authentication.getName();
    }
}
