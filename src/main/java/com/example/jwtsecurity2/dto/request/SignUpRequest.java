package com.example.jwtsecurity2.dto.request;

import com.example.jwtsecurity2.entity.Role;
import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private Role role;
}
