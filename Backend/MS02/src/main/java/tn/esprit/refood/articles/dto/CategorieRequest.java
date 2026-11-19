package tn.esprit.refood.articles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategorieRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "100 caractères maximum")
        String nom,

        @Size(max = 255, message = "255 caractères maximum")
        String description) {
}
