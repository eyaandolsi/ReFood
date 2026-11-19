package tn.esprit.refood.donations.dto;

import tn.esprit.refood.donations.entity.Besoin;
import tn.esprit.refood.donations.entity.Urgence;
import java.time.LocalDate;

public record BesoinResponse(Long id, Long associationId, String categorie, int quantite,
                             Urgence urgence, LocalDate dateLimite) {
    public static BesoinResponse from(Besoin b) {
        return new BesoinResponse(b.getId(), b.getAssociation().getId(), b.getCategorie(),
                b.getQuantite(), b.getUrgence(), b.getDateLimite());
    }
}
