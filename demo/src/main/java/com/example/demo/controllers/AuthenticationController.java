package com.example.demo.controllers;

import com.example.demo.requests.AuthenticationRequest;
import com.example.demo.requests.RegisterRequest;
import com.example.demo.responses.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        System.out.println("Контроллер отрабатывает");
        System.out.println(request.getX());
        System.out.println(request.getY());
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request
    ){
        System.out.println(request);

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
