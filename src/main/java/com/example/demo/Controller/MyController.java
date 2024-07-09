package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.Repo.Service.AuthenticationService;
import com.example.demo.Repo.Service.JwtService;
import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/auth")
@RestController
public class MyController{
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public MyController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    
    @GetMapping("/homes")
    public String anu() {
    	  System.out.println("Endpoint /homes accessed");
    	return "working";
    }
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
          System.out.println("hey n,ghjhj");
        return ResponseEntity.ok(registeredUser);
    }

   @PostMapping("/login")
    
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        // Authenticate user
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Generate JWT token
        String jwtToken = jwtService.generateToken(authenticatedUser);
System.out.println(jwtToken);
        // Get expiration time
        long expiresIn = jwtService.getExpirationTime();

        // Create LoginResponse object
        LoginResponse loginResponse = new LoginResponse(jwtToken, expiresIn);
        	System.out.println(loginResponse);
        return ResponseEntity.ok(loginResponse);
    }
}