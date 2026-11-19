package tn.esprit.refood.donations.dto;

import tn.esprit.refood.donations.entity.Don;
import tn.esprit.refood.donations.entity.StatutDon;
import java.time.LocalDateTime;

public record DonResponse(Long id, Long offreId, Long associationId, int quantite, StatutDon statut,
                          LocalDateTime dateDemande, LocalDateTime dateReception, String motifRefus,
                          CreneauResponse creneau) {
    public static DonResponse from(Don d) {
        return new DonResponse(d.getId(), d.getOffreId(), d.getAssociation().getId(), d.getQuantite(),
                d.getStatut(), d.getDateDemande(), d.getDateReception(), d.getMotifRefus(),
                CreneauResponse.from(d.getCreneau()));
    }
}
