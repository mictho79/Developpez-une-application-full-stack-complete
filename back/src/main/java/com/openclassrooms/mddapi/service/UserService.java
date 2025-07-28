package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

/**
 * Service de gestion des informations utilisateur.
 * Permet de récupérer et de mettre à jour les données du profil utilisateur.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param userRepository   repository des utilisateurs
     * @param passwordEncoder  utilitaire de hash des mots de passe
     */
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère les informations de l'utilisateur actuellement connecté.
     *
     * @param principal l'objet représentant l'utilisateur authentifié
     * @return un Optional contenant l'utilisateur si trouvé
     */
    public Optional<User> getCurrentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }

    /**
     * Met à jour les données d'un utilisateur connecté.
     *
     * @param principal l'utilisateur actuellement authentifié
     * @param dto       les nouvelles données à appliquer
     * @return un Optional contenant l'utilisateur mis à jour ou vide si non trouvé
     */
    public Optional<User> updateUser(Principal principal, UserDTO dto) {
        return userRepository.findByEmail(principal.getName())
                .map(user -> {
                    user.setUsername(dto.getUsername());
                    user.setEmail(dto.getEmail());

                    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                        user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    }

                    return userRepository.save(user);
                });
    }
}
