package com.example.jwtsecurity2.dto.response;

import com.example.jwtsecurity2.entity.OurUsers;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpResponse {

    private String email;
    private String message;
    private int statusCode;
    private String error;

}
