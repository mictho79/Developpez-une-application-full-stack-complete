package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.LoginDTO;
import com.openclassrooms.mddapi.dto.LoginResponseDTO;
import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginDTO loginDTO) {

        // Recherche par username ou email
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .or(() -> userRepository.findByEmail(loginDTO.getUsername()))
                .orElseThrow(() -> {
                    return new BadCredentialsException("Identifiants invalides");
                });


        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Identifiants invalides");
        }


        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponseDTO(token);
    }


    public LoginResponseDTO register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Nom d’utilisateur déjà utilisé");
        }
        if (!registerDTO.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")) {
            throw new RuntimeException("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre");
        }

        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());

        User user = User.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(encodedPassword)
                .admin(false)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponseDTO(token);
    }

}
