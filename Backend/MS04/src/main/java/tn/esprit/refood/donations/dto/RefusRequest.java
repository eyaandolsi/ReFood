package tn.esprit.refood.donations.dto;

import jakarta.validation.constraints.NotBlank;

public record RefusRequest(@NotBlank String motif) {
}
