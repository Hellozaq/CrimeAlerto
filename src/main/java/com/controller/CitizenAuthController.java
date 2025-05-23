package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CitizenDTO.CitizenLogin;
import com.dto.CitizenDTO.CitizenRegister;
import com.service.CitizenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/citizen")
public class CitizenAuthController {
    private final CitizenService citizenService;

    public CitizenAuthController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CitizenRegister citizenRegister) {
        citizenService.register(citizenRegister);
        return ResponseEntity.ok("User registered successfully. Please log in.");    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CitizenLogin CitizenLogin, HttpServletResponse response) {
        String token = citizenService.login(CitizenLogin);
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(10*60*60);
        response.addCookie(cookie);
        return ResponseEntity.ok("Login successfull. Redirecting to Citizen Dashboard "+token);
    }

}
