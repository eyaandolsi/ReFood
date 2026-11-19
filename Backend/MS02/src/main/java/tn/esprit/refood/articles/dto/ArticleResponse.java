package tn.esprit.refood.articles.dto;

import tn.esprit.refood.articles.entity.StatutArticle;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String titre,
        String contenu,
        String urlImage,
        StatutArticle statut,
        String auteurId,
        Long categorieId,
        String categorieNom,
        LocalDateTime creeLe,
        LocalDateTime modifieLe,
        long nbLikes,
        long nbCommentaires) {
}
