package tn.esprit.refood.articles.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(nullable = false, length = 10000)
    private String contenu;

    @Column(length = 500)
    private String urlImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutArticle statut = StatutArticle.BROUILLON;

    /** Identifiant de l'auteur (sera remplacé par le "sub" du JWT Keycloak en séance 8). */
    @Column(nullable = false, length = 100)
    private String auteurId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creeLe;

    private LocalDateTime modifieLe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @PrePersist
    void onCreate() {
        creeLe = LocalDateTime.now();
        modifieLe = creeLe;
    }

    @PreUpdate
    void onUpdate() {
        modifieLe = LocalDateTime.now();
    }
}
