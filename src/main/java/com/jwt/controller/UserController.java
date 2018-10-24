package com.jwt.controller;

import com.jwt.security.JwtAuthenticationResponse;
import com.jwt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> login() throws MessagingException {
        String token = userService.login("test", "123456");
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINA')")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() throws MessagingException {
        return "hello";
    }
}
