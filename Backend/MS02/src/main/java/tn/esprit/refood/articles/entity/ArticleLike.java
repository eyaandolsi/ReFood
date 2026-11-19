package tn.esprit.refood.articles.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** Like d'un article ("Like" est un mot réservé SQL/HQL, d'où le nom ArticleLike). */
@Entity
@Table(name = "article_likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"article_id", "utilisateur_id"}))
@Getter
@Setter
@NoArgsConstructor
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "utilisateur_id", nullable = false, length = 100)
    private String utilisateurId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creeLe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @PrePersist
    void onCreate() {
        creeLe = LocalDateTime.now();
    }
}
