package tn.esprit.refood.donations.dto;

import tn.esprit.refood.donations.entity.HistoriqueDon;
import tn.esprit.refood.donations.entity.StatutDon;
import java.time.LocalDateTime;

public record HistoriqueResponse(StatutDon ancienStatut, StatutDon nouveauStatut, LocalDateTime dateChangement) {
    public static HistoriqueResponse from(HistoriqueDon h) {
        return new HistoriqueResponse(h.getAncienStatut(), h.getNouveauStatut(), h.getDateChangement());
    }
}
