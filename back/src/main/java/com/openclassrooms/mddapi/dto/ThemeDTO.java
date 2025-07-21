package com.openclassrooms.mddapi.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeDTO {
    private Long id;
    private String title;
    private String description;
}
