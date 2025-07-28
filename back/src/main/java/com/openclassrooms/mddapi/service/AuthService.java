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

/**
 * Service d'authentification permettant la gestion de la connexion
 * et de l'inscription des utilisateurs.
 */
@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authentifie un utilisateur à partir de ses identifiants (email ou username + mot de passe).
     *
     * @param loginDTO les identifiants de connexion
     * @return un objet contenant le token JWT généré
     * @throws BadCredentialsException si les identifiants sont invalides
     */
    public LoginResponseDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .or(() -> userRepository.findByEmail(loginDTO.getUsername()))
                .orElseThrow(() -> new BadCredentialsException("Identifiants invalides"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Identifiants invalides");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponseDTO(token);
    }

    /**
     * Inscrit un nouvel utilisateur après validation des champs.
     *
     * @param registerDTO les informations d'inscription (username, email, mot de passe)
     * @return un objet contenant le token JWT généré
     * @throws RuntimeException si l'email ou le username existe déjà, ou si le mot de passe est invalide
     */
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
