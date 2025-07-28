package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des abonnements aux thèmes.
 * Permet de s’abonner, se désabonner et consulter les abonnements de l’utilisateur.
 */
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;

    /**
     * Permet à un utilisateur de s’abonner à un thème.
     *
     * @param email   l’email de l’utilisateur
     * @param themeId l’identifiant du thème
     * @throws RuntimeException si l’utilisateur ou le thème n’est pas trouvé
     */
    public void subscribe(String email, Long themeId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Thème introuvable"));

        if (!user.getSubscribedThemes().contains(theme)) {
            user.getSubscribedThemes().add(theme);
            userRepository.save(user);
        }
    }

    /**
     * Permet à un utilisateur de se désabonner d’un thème.
     *
     * @param email   l’email de l’utilisateur
     * @param themeId l’identifiant du thème
     * @throws RuntimeException si l’utilisateur ou le thème n’est pas trouvé
     */
    public void unsubscribe(String email, Long themeId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Thème introuvable"));

        if (user.getSubscribedThemes().contains(theme)) {
            user.getSubscribedThemes().remove(theme);
            userRepository.save(user);
        }
    }

    /**
     * Récupère la liste des thèmes auxquels l’utilisateur est abonné.
     *
     * @param email l’email de l’utilisateur
     * @return la liste des thèmes suivis
     * @throws RuntimeException si l’utilisateur n’est pas trouvé
     */
    public List<Theme> getUserSubscriptions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return user.getSubscribedThemes();
    }
}
