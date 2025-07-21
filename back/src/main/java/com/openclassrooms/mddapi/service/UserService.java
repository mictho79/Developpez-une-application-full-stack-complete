package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Renvoie l'utilisateur connecté à partir du principal
    public Optional<User> getCurrentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }

    // Met à jour les informations de l'utilisateur (username, email, + password si fourni)
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
