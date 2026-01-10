package com.usermanagement.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest req) {
        authService.signup(
            req.getUsername(),
            req.getPassword(),
            req.getRole()
        );
        return "Signup successful";
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
    	
       String token = authService.login(req);
       
       return ResponseEntity.status(HttpStatus.OK).body( new LoginResponse(token));
    }
}
