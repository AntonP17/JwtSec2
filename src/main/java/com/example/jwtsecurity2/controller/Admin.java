package com.example.jwtsecurity2.controller;


import com.example.jwtsecurity2.dto.request.LockedUser;
import com.example.jwtsecurity2.dto.response.UserUnlockResponse;
import com.example.jwtsecurity2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class Admin {

    @Autowired
    private AuthService authService;

    @PostMapping("/unlock")
    public ResponseEntity<UserUnlockResponse> unlockUser(@RequestBody LockedUser email) {
        return ResponseEntity.ok(authService.unlockUser(email.getEmail()));
    }

    @PostMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok("Users list");
    }
}
