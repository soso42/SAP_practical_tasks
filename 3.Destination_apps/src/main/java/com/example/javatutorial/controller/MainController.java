package com.example.javatutorial.controller;

import com.example.javatutorial.exception.NotAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.sap.cloud.security.xsuaa.token.Token;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "")
@Slf4j
public class MainController {

    @GetMapping(path = "")
    public ResponseEntity<?> readAll(@AuthenticationPrincipal Token token) {
        if (!token.getAuthorities().contains(new SimpleGrantedAuthority("Display"))) {
            log.info("User {} was denied entry", token.getEmail());
            throw new NotAuthorizedException("This operation requires \"Display\" scope");
        }

        log.info("User {} was logged in", token.getEmail());

        Map<String, String> result = new HashMap<>();
        result.put("Logged in user authorities", token.getAuthorities().toString());
        result.put("Logged in user email", token.getEmail());
        result.put("Logged in user id", token.getClientId());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
