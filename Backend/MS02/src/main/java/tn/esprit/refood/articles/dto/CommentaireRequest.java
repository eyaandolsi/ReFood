package tn.esprit.refood.articles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentaireRequest(
        @NotBlank(message = "Le contenu est obligatoire")
        @Size(max = 2000, message = "2000 caractères maximum")
        String contenu,

        @NotBlank(message = "L'auteur est obligatoire")
        String auteurId) {
}
