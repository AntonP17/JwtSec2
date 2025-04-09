package com.example.jwtsecurity2.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {
    private int StatusCode;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String message;
    private String error;

}
