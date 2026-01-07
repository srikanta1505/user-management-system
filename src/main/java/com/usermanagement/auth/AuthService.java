package com.usermanagement.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermanagement.user.User;
import com.usermanagement.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo,
                       PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
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
}
