package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "themes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "subscribedThemes")
    private List<User> subscribers;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<Article> articles;
}
