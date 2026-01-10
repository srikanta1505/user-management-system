package com.usermanagement.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.usermanagement.security.JwtUtil;
import com.usermanagement.user.User;
import com.usermanagement.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository repo,
                       PasswordEncoder encoder,JwtUtil jwtUtil) {
        this.repo 	 = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public void signup(String username,
                       String password,
                       String role) {

        if (repo.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole(role == null ? "USER" : role);

        repo.save(user);
    }
    
    public String login(@RequestBody LoginRequest request) {
    	
    	User user = repo.findByUsername(request.getUsername())
    			.orElseThrow(() -> new RuntimeException("Invalid Credentials"));
    	
    	if(!encoder.matches(request.getPassword(), user.getPassword()))
    	{
    		throw new RuntimeException("Invalid Credentials");
    	}
    	
    	return jwtUtil.generateToken(request.getUsername());
    }
}
