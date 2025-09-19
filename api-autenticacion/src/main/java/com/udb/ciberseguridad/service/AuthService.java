package com.udb.ciberseguridad.service;

import com.udb.ciberseguridad.model.User;
import com.udb.ciberseguridad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Map<String, Object> register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }

        if (userRepository.existsByCarnet(user.getCarnet())) {
            throw new RuntimeException("Carnet ya registrado");
        }

        String role = user.getEmail().endsWith("@docentes.udb.edu.sv") ? "DOCENTE" : "ESTUDIANTE";
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", savedUser);
        response.put("message", "Usuario registrado exitosamente");

        return response;
    }

    public Map<String, Object> login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);
        response.put("message", "Login exitoso");

        return response;
    }
}