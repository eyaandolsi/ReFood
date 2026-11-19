package tn.esprit.refood.donations.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreneauRequest(@NotNull @Future LocalDateTime dateDebut,
                             @NotNull @Future LocalDateTime dateFin) {
}
