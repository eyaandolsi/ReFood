package tn.esprit.refood.donations.dto;

import tn.esprit.refood.donations.entity.Urgence;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record BesoinRequest(
        @NotNull Long associationId,
        @NotBlank String categorie,
        @Positive int quantite,
        @NotNull Urgence urgence,
        @FutureOrPresent LocalDate dateLimite) {
}
