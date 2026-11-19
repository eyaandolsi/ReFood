package tn.esprit.refood.donations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AssociationRequest(
        @NotBlank String nom,
        String adresse,
        @Size(max = 20) String telephone) {
}
