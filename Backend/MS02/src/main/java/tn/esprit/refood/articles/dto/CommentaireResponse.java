package tn.esprit.refood.articles.dto;

import tn.esprit.refood.articles.entity.StatutCommentaire;

import java.time.LocalDateTime;

public record CommentaireResponse(
        Long id,
        String contenu,
        String auteurId,
        LocalDateTime creeLe,
        StatutCommentaire statut,
        Long articleId) {
}
