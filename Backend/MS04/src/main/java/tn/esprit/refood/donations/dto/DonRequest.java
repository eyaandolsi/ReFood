package tn.esprit.refood.donations.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DonRequest(
        @NotNull Long offreId,
        @NotNull Long associationId,
        @Positive int quantite) {
}
