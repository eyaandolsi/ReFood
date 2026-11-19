package tn.esprit.refood.donations.dto;

import tn.esprit.refood.donations.entity.CreneauRetrait;
import java.time.LocalDateTime;

public record CreneauResponse(Long id, LocalDateTime dateDebut, LocalDateTime dateFin, boolean confirme) {
    public static CreneauResponse from(CreneauRetrait c) {
        return c == null ? null : new CreneauResponse(c.getId(), c.getDateDebut(), c.getDateFin(), c.isConfirme());
    }
}
