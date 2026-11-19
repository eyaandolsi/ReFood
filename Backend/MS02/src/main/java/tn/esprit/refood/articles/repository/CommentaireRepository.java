package tn.esprit.refood.articles.repository;

import tn.esprit.refood.articles.entity.Commentaire;
import tn.esprit.refood.articles.entity.StatutCommentaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    List<Commentaire> findByArticleIdAndStatutOrderByCreeLeAsc(Long articleId, StatutCommentaire statut);

    long countByArticleId(Long articleId);

    void deleteByArticleId(Long articleId);
}
