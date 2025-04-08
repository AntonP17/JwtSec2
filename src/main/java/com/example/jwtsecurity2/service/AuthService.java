package com.example.jwtsecurity2.service;

import com.example.jwtsecurity2.dto.ReqRes;
import com.example.jwtsecurity2.entity.OurUsers;
import com.example.jwtsecurity2.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final Map<String, Integer> loginAttemptsCache = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;

    public ReqRes signUp(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();
        try {
            OurUsers ourUsers = new OurUsers();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setRole(registrationRequest.getRole());
            OurUsers ourUserResult = ourUserRepo.save(ourUsers);
            if (ourUserResult != null && ourUserResult.getId()>0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(ReqRes signinRequest) {
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: " + user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");

            loginAttemptsCache.remove(signinRequest.getEmail()); // Clear login attempts for the user
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());

            String email = signinRequest.getEmail();
            int attempts = loginAttemptsCache.getOrDefault(email, 0) + 1;
            loginAttemptsCache.put(email, attempts);

            if (attempts >= MAX_ATTEMPTS) {
                OurUsers user = ourUserRepo.findByEmail(email).orElseThrow();
                user.setAccountNonLocked(false);
                ourUserRepo.save(user);
                response.setMessage("Account locked due to 5 failed attempts");
            }
            return response;
        }
        return response;
    }

    public ReqRes unlockUser(String email) {
        OurUsers user = ourUserRepo.findByEmail(email).orElseThrow();
        user.setAccountNonLocked(true);
        ourUserRepo.save(user);
        loginAttemptsCache.remove(email); // Сбрасываем счетчик

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Account unlocked");
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        OurUsers users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }
}
