package com.openclassrooms.mddapi.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
    private String username;
    private String password;
}
