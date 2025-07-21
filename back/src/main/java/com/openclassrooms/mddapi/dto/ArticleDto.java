package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Article;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String themeTitle;
    private LocalDateTime createdAt;

    public static ArticleDto fromEntity(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorUsername(article.getAuthor().getUsername())
                .themeTitle(article.getTheme().getTitle())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
