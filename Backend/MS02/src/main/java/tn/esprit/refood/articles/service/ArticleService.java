package tn.esprit.refood.articles.service;

import tn.esprit.refood.articles.dto.ArticleRequest;
import tn.esprit.refood.articles.dto.ArticleResponse;
import tn.esprit.refood.articles.entity.Article;
import tn.esprit.refood.articles.entity.Categorie;
import tn.esprit.refood.articles.entity.StatutArticle;
import tn.esprit.refood.articles.exception.ConflictException;
import tn.esprit.refood.articles.exception.ResourceNotFoundException;
import tn.esprit.refood.articles.repository.ArticleLikeRepository;
import tn.esprit.refood.articles.repository.ArticleRepository;
import tn.esprit.refood.articles.repository.CategorieRepository;
import tn.esprit.refood.articles.repository.CommentaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategorieRepository categorieRepository;
    private final CommentaireRepository commentaireRepository;
    private final ArticleLikeRepository likeRepository;

    public ArticleResponse create(ArticleRequest request) {
        Article a = new Article();
        a.setStatut(StatutArticle.BROUILLON);
        a.setAuteurId(request.auteurId());
        apply(a, request);
        return toResponse(articleRepository.save(a));
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> search(StatutArticle statut, Long categorieId) {
        return articleRepository.search(statut, categorieId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ArticleResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public ArticleResponse update(Long id, ArticleRequest request) {
        Article a = getOrThrow(id);
        apply(a, request);
        return toResponse(articleRepository.save(a));
    }

    /** BROUILLON -> PUBLIE. */
    public ArticleResponse publish(Long id) {
        Article a = getOrThrow(id);
        if (a.getStatut() != StatutArticle.BROUILLON) {
            throw new ConflictException("Seul un brouillon peut être publié (statut actuel : " + a.getStatut() + ")");
        }
        a.setStatut(StatutArticle.PUBLIE);
        return toResponse(articleRepository.save(a));
    }

    /** PUBLIE -> ARCHIVE. */
    public ArticleResponse archive(Long id) {
        Article a = getOrThrow(id);
        if (a.getStatut() != StatutArticle.PUBLIE) {
            throw new ConflictException("Seul un article publié peut être archivé (statut actuel : " + a.getStatut() + ")");
        }
        a.setStatut(StatutArticle.ARCHIVE);
        return toResponse(articleRepository.save(a));
    }

    public void delete(Long id) {
        Article a = getOrThrow(id);
        commentaireRepository.deleteByArticleId(id);
        likeRepository.deleteByArticleId(id);
        articleRepository.delete(a);
    }

    // ---------- utilitaires ----------

    private Article getOrThrow(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article introuvable : " + id));
    }

    private void apply(Article a, ArticleRequest r) {
        Categorie c = categorieRepository.findById(r.categorieId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable : " + r.categorieId()));
        a.setTitre(r.titre());
        a.setContenu(r.contenu());
        a.setUrlImage(r.urlImage());
        a.setCategorie(c);
    }

    private ArticleResponse toResponse(Article a) {
        return new ArticleResponse(
                a.getId(), a.getTitre(), a.getContenu(), a.getUrlImage(), a.getStatut(), a.getAuteurId(),
                a.getCategorie().getId(), a.getCategorie().getNom(),
                a.getCreeLe(), a.getModifieLe(),
                likeRepository.countByArticleId(a.getId()),
                commentaireRepository.countByArticleId(a.getId()));
    }
}
