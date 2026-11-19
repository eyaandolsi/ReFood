package tn.esprit.refood.articles.repository;

import tn.esprit.refood.articles.entity.Article;
import tn.esprit.refood.articles.entity.StatutArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    boolean existsByCategorieId(Long categorieId);

    @Query("""
            select a from Article a
            where (:statut is null or a.statut = :statut)
              and (:categorieId is null or a.categorie.id = :categorieId)
            order by a.creeLe desc
            """)
    List<Article> search(@Param("statut") StatutArticle statut,
                         @Param("categorieId") Long categorieId);
}
