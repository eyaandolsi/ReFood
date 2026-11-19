package tn.esprit.refood.articles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArticleRequest(
        @NotBlank(message = "Le titre est obligatoire")
        @Size(max = 200, message = "200 caractères maximum")
        String titre,

        @NotBlank(message = "Le contenu est obligatoire")
        @Size(max = 10000, message = "10000 caractères maximum")
        String contenu,

        @Size(max = 500, message = "500 caractères maximum")
        String urlImage,

        @NotNull(message = "La catégorie est obligatoire")
        Long categorieId,

        @NotBlank(message = "L'auteur est obligatoire")
        String auteurId) {
}
