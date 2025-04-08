package com.example.jwtsecurity2.dto;

import com.example.jwtsecurity2.entity.OurUsers;
import com.example.jwtsecurity2.entity.Product;
import com.example.jwtsecurity2.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private Role role;
    private String password;
    private List<Product> products;
    private OurUsers ourUsers;
}
