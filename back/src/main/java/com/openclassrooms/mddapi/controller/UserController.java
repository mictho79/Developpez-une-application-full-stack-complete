    package com.openclassrooms.mddapi.controller;

    import com.openclassrooms.mddapi.dto.UserDTO;
    import com.openclassrooms.mddapi.model.User;
    import com.openclassrooms.mddapi.service.UserService;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.security.Principal;
    import java.util.Optional;

    @RestController
    @RequestMapping("/api/users")
    @Tag(name = "User")
    public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        // GET /api/users/me
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

        // PATCH /api/users/me
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
