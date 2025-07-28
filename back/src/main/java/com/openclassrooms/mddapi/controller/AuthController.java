package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.LoginDTO;
import com.openclassrooms.mddapi.dto.LoginResponseDTO;
import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour les opérations d'authentification.
 * Permet de connecter ou d'inscrire un utilisateur et de générer un token JWT.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Connecte un utilisateur avec ses identifiants.
     *
     * @param loginDTO Identifiants de l'utilisateur (email + mot de passe)
     * @return Réponse contenant le token JWT si la connexion est réussie
     */
    @Operation(summary = "Connexion de l'utilisateur")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    /**
     * Inscrit un nouvel utilisateur avec les informations fournies.
     *
     * @param registerDTO Données de l'utilisateur à créer (email, username, mot de passe)
     * @return Réponse contenant le token JWT une fois l'inscription réussie
     */
    @Operation(summary = "Inscription d'un nouvel utilisateur")
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(authService.register(registerDTO));
    }
}
