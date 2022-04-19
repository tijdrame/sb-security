package com.boa.api.sbsecurity.response;

import lombok.Data;

@Data
public class LoginResponse extends GenericResponse{
    private String idToken;
    private String refreshToken;
}
