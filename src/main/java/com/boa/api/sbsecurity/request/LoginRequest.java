package com.boa.api.sbsecurity.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    @Size(min = 1, max = 50)
    private String username;
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private Boolean rememberMe;
}
