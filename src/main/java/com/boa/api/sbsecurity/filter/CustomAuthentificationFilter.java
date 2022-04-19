package com.boa.api.sbsecurity.filter;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.boa.api.sbsecurity.security.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String args = null, username = null, password = null;
        try {
            args = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            JSONObject json = new JSONObject(args);
            log.info("test [{}]", args);
            log.info("json [{}]", json.getString("username"));
            if (json != null) {
                username = json.getString("username");
                password = json.getString("password");

                // String password = request.getParameter("password"); pour form encoded
                log.info("Username is [{}]", username);
                log.info("Password is [{}]", password);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username, password);
                return authenticationManager.authenticate(authenticationToken);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        // Secret doit être crypter et garder qq part
        byte[] bytesEncoded = Base64.encodeBase64(SecurityUtils.SECRET_KEY.getBytes());
        Algorithm algorithm = Algorithm.HMAC256(bytesEncoded);
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityUtils.TOKEN_LIFETIME_DURATION)) // 10 mn
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityUtils.ACCESS_TOKEN_LIFETIME_DURATION)) // 30 mn
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        /*
         * ancien
         * response.setHeader("access_token", accessToken);
         * response.setHeader("refresh_token", refreshToken);
         */
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("code", HttpServletResponse.SC_OK);
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        tokens.put("dateResponse", Instant.now().toString());
        response.setHeader(SecurityUtils.AUTHORIZATION_HEADER, "Bearer "+ accessToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    // par exemple bloquer le user aprés 5 tentatives infructueuses pendant 10 mn
    // (bloquer les brutes attack)
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> failure = new HashMap<>();
        failure.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        failure.put("dateResponse", Instant.now().toString());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), failure);
    }

}
