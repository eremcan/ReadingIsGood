package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.AuthenticationDto;
import com.getir.readingisgood.dto.request.AuthenticationRequest;
import com.getir.readingisgood.security.CustomUserDetailsService;
import com.getir.readingisgood.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class SecurityController {


    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;

    @Autowired
    public SecurityController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException badCredentialsException) {
            throw new Exception("Invalid Login Credentials.");
        }

        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(authenticationRequest.getPassword());
        final String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationDto(jwt));

    }
}
