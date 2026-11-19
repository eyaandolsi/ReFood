package tn.esprit.refood.articles.service;

import tn.esprit.refood.articles.dto.LikeResponse;
import tn.esprit.refood.articles.entity.Article;
import tn.esprit.refood.articles.entity.ArticleLike;
import tn.esprit.refood.articles.entity.StatutArticle;
import tn.esprit.refood.articles.exception.BadRequestException;
import tn.esprit.refood.articles.exception.ConflictException;
import tn.esprit.refood.articles.exception.ResourceNotFoundException;
import tn.esprit.refood.articles.repository.ArticleLikeRepository;
import tn.esprit.refood.articles.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final ArticleLikeRepository likeRepository;
    private final ArticleRepository articleRepository;

    public LikeResponse like(Long articleId, String utilisateurId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article introuvable : " + articleId));
        if (article.getStatut() != StatutArticle.PUBLIE) {
            throw new BadRequestException("On ne peut aimer qu'un article publié");
        }
        if (likeRepository.existsByArticleIdAndUtilisateurId(articleId, utilisateurId)) {
            throw new ConflictException("Article déjà aimé par cet utilisateur");
        }
        ArticleLike like = new ArticleLike();
        like.setArticle(article);
        like.setUtilisateurId(utilisateurId);
        likeRepository.save(like);
        return new LikeResponse(articleId, utilisateurId, likeRepository.countByArticleId(articleId));
    }

    public void unlike(Long articleId, String utilisateurId) {
        ArticleLike like = likeRepository.findByArticleIdAndUtilisateurId(articleId, utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Like introuvable pour cet article et cet utilisateur"));
        likeRepository.delete(like);
    }
}
