package com.openclassrooms.mddapi.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleRequestDto {
    private String title;
    private String content;
    private Long themeId;
}
