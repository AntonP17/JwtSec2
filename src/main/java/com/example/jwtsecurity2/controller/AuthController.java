package com.example.jwtsecurity2.controller;


import com.example.jwtsecurity2.dto.request.RefreshTokenDTO;
import com.example.jwtsecurity2.dto.request.SignInRequest;
import com.example.jwtsecurity2.dto.request.SignUpRequest;
import com.example.jwtsecurity2.dto.response.RefreshTokenResponse;
import com.example.jwtsecurity2.dto.response.SignInResponse;
import com.example.jwtsecurity2.dto.response.SignUpResponse;
import com.example.jwtsecurity2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenDTO refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
