package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;

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

    public List<Theme> getUserSubscriptions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return user.getSubscribedThemes();
    }
}
