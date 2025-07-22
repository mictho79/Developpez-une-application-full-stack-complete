package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Utilisateur", description = "Opérations sur l'utilisateur connecté")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Récupérer les infos de l'utilisateur connecté")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        Optional<User> userOpt = userService.getCurrentUser(principal);
        return userOpt.map(user -> ResponseEntity.ok(
                UserDTO.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()
        )).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Mettre à jour les infos de l'utilisateur")
    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO dto, Principal principal) {
        Optional<User> updatedUser = userService.updateUser(principal, dto);
        return updatedUser.map(user -> ResponseEntity.ok(
                UserDTO.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()
        )).orElse(ResponseEntity.notFound().build());
    }
}
