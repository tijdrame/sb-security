package com.boa.api.sbsecurity.web;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.validation.Valid;

import com.boa.api.sbsecurity.filter.CustomAuthentificationFilter;
import com.boa.api.sbsecurity.request.LoginRequest;
import com.boa.api.sbsecurity.response.LoginResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginResource {

    //private final AuthenticationManager authenticationManager; 
    //private final CustomAuthentificationFilter customAuthentificationFilter ;

    /*public LoginResource(AuthenticationManager authenticationManager, CustomAuthentificationFilter customAuthentificationFilter){
        this.authenticationManager = authenticationManager;
        this.customAuthentificationFilter = customAuthentificationFilter;
    }
    
    /*@PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody LoginRequest lRequest) throws IOException, ServletException {
        
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(lRequest.getUsername(), lRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        LoginResponse response = customAuthentificationFilter.successfulAuthentication(authenticationToken);
        if(response!=null && response.getIdToken()!=null) {
            response.setCode("200");
            response.setDescription("OK");
        }
        else {
            response.setCode("405");
            response.setDescription("KO");
        }
        response.setDateResponse(Instant.now());
        
        return null;
    }*/
}
