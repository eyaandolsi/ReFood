package tn.esprit.refood.articles.repository;

import tn.esprit.refood.articles.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    boolean existsByArticleIdAndUtilisateurId(Long articleId, String utilisateurId);

    Optional<ArticleLike> findByArticleIdAndUtilisateurId(Long articleId, String utilisateurId);

    long countByArticleId(Long articleId);

    void deleteByArticleId(Long articleId);
}
