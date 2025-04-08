package com.example.jwtsecurity2.controller;

import com.example.jwtsecurity2.dto.ReqRes;
import com.example.jwtsecurity2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class Admin {

    @Autowired
    private AuthService authService;

    @PostMapping("/unlock")
    public ResponseEntity<ReqRes> unlockUser(@RequestParam String email) {
        return ResponseEntity.ok(authService.unlockUser(email));
    }

    @PostMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok("Users list");
    }

    @PostMapping("/changeRole")
    public ResponseEntity<String> changeRole(@RequestParam String email, @RequestParam String role) {
        return ResponseEntity.ok("Role changed");
    }
}
