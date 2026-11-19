package tn.esprit.refood.articles.dto;

public record LikeResponse(Long articleId, String utilisateurId, long nbLikes) {
}
