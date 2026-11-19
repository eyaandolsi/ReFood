package tn.esprit.refood.articles.dto;

import jakarta.validation.constraints.NotBlank;

public record LikeRequest(
        @NotBlank(message = "L'utilisateur est obligatoire")
        String utilisateurId) {
}
