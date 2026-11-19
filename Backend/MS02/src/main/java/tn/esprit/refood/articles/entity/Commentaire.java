package tn.esprit.refood.articles.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "commentaires")
@Getter
@Setter
@NoArgsConstructor
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String contenu;

    @Column(nullable = false, length = 100)
    private String auteurId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creeLe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutCommentaire statut = StatutCommentaire.VISIBLE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @PrePersist
    void onCreate() {
        creeLe = LocalDateTime.now();
    }
}
