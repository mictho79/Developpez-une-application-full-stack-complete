package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des thèmes disponibles et suivis.
 */
@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    /**
     * Constructeur injectant les dépendances nécessaires.
     *
     * @param themeRepository repository des thèmes
     * @param userRepository  repository des utilisateurs
     */
    public ThemeService(ThemeRepository themeRepository, UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Récupère tous les thèmes disponibles.
     *
     * @return liste de tous les thèmes sous forme de DTO
     */
    public List<ThemeDTO> getAllThemes() {
        return themeRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    /**
     * Récupère les thèmes auxquels un utilisateur est abonné.
     *
     * @param email l'email de l'utilisateur
     * @return liste des thèmes suivis sous forme de DTO
     */
    public List<ThemeDTO> getUserSubscriptions(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return List.of();

        return userOpt.get().getSubscribedThemes().stream().map(this::mapToDTO).toList();
    }

    /**
     * Convertit une entité Theme en DTO.
     *
     * @param theme l'entité Theme
     * @return un DTO contenant les données du thème
     */
    private ThemeDTO mapToDTO(Theme theme) {
        return ThemeDTO.builder()
                .id(theme.getId())
                .title(theme.getTitle())
                .description(theme.getDescription())
                .build();
    }
}
