package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    public ThemeService(ThemeRepository themeRepository, UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
    }

    public List<ThemeDTO> getAllThemes() {
        return themeRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public List<ThemeDTO> getUserSubscriptions(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return List.of();

        return userOpt.get().getSubscribedThemes().stream().map(this::mapToDTO).toList();
    }

    private ThemeDTO mapToDTO(Theme theme) {
        return ThemeDTO.builder()
                .id(theme.getId())
                .title(theme.getTitle())
                .description(theme.getDescription())
                .build();
    }
}
