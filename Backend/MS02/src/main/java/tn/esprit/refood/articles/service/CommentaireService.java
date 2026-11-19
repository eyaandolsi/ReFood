package tn.esprit.refood.articles.service;

import tn.esprit.refood.articles.dto.CommentaireRequest;
import tn.esprit.refood.articles.dto.CommentaireResponse;
import tn.esprit.refood.articles.entity.Article;
import tn.esprit.refood.articles.entity.Commentaire;
import tn.esprit.refood.articles.entity.StatutArticle;
import tn.esprit.refood.articles.entity.StatutCommentaire;
import tn.esprit.refood.articles.exception.BadRequestException;
import tn.esprit.refood.articles.exception.ForbiddenException;
import tn.esprit.refood.articles.exception.ResourceNotFoundException;
import tn.esprit.refood.articles.repository.ArticleRepository;
import tn.esprit.refood.articles.repository.CommentaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<CommentaireResponse> findByArticle(Long articleId) {
        getArticleOrThrow(articleId);
        return commentaireRepository
                .findByArticleIdAndStatutOrderByCreeLeAsc(articleId, StatutCommentaire.VISIBLE)
                .stream().map(this::toResponse).toList();
    }

    public CommentaireResponse add(Long articleId, CommentaireRequest request) {
        Article article = getArticleOrThrow(articleId);
        if (article.getStatut() != StatutArticle.PUBLIE) {
            throw new BadRequestException("On ne peut commenter qu'un article publié");
        }
        Commentaire c = new Commentaire();
        c.setArticle(article);
        c.setContenu(request.contenu());
        c.setAuteurId(request.auteurId());
        c.setStatut(StatutCommentaire.VISIBLE);
        return toResponse(commentaireRepository.save(c));
    }

    /** Seul l'auteur peut modifier son commentaire (403 sinon). */
    public CommentaireResponse update(Long id, CommentaireRequest request) {
        Commentaire c = getOrThrow(id);
        checkOwner(c, request.auteurId());
        c.setContenu(request.contenu());
        return toResponse(commentaireRepository.save(c));
    }

    /** Seul l'auteur peut supprimer son commentaire (403 sinon). */
    public void delete(Long id, String auteurId) {
        Commentaire c = getOrThrow(id);
        checkOwner(c, auteurId);
        commentaireRepository.delete(c);
    }

    private void checkOwner(Commentaire c, String auteurId) {
        if (!c.getAuteurId().equals(auteurId)) {
            throw new ForbiddenException("Vous ne pouvez agir que sur vos propres commentaires");
        }
    }

    private Article getArticleOrThrow(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article introuvable : " + id));
    }

    private Commentaire getOrThrow(Long id) {
        return commentaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commentaire introuvable : " + id));
    }

    private CommentaireResponse toResponse(Commentaire c) {
        return new CommentaireResponse(c.getId(), c.getContenu(), c.getAuteurId(),
                c.getCreeLe(), c.getStatut(), c.getArticle().getId());
    }
}
