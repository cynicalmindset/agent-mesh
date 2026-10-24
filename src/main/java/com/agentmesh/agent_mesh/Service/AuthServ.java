package com.agentmesh.agent_mesh.Service;

import com.agentmesh.agent_mesh.DTO.AuthResponse;
import com.agentmesh.agent_mesh.DTO.LoginInReq;
import com.agentmesh.agent_mesh.DTO.SignUpReq;
import com.agentmesh.agent_mesh.Model.User;
import com.agentmesh.agent_mesh.Repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServ {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final Jwt jwt;

    public AuthServ(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            Jwt jwt) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    public AuthResponse signup(SignUpReq request) {
        String email = request.email().trim().toLowerCase();
        String password = request.password();

        if (email.isBlank() || password.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email and password are required");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a valid email");
        }

        if (password.length() < 6) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password must contain at least 6 characters");
        }

        if (userRepo.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An account with this email already exists");
        }
        User user = new User(email, passwordEncoder.encode(password));
        userRepo.save(user);

        String token = jwt.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail());
    }

    public AuthResponse login(LoginInReq request) {
        String email = request.email().trim().toLowerCase();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"));

        if (!passwordEncoder.matches(
                request.password(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password");
        }

        String token = jwt.generateToken(user.getEmail());

        return new AuthResponse(token, user.getEmail());
    }
}
