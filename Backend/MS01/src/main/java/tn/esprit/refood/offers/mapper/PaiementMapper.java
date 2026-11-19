package tn.esprit.refood.offers.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.refood.offers.dto.PaiementRequestDTO;
import tn.esprit.refood.offers.dto.PaiementResponseDTO;
import tn.esprit.refood.offers.entity.Paiement;

/**
 * Mapper pour convertir entre Entity et DTO pour Paiement.
 */
@Component
public class PaiementMapper {

    public PaiementResponseDTO toResponseDTO(Paiement paiement) {
        if (paiement == null) {
            return null;
        }

        PaiementResponseDTO dto = new PaiementResponseDTO();
        dto.setId(paiement.getId());
        dto.setMontant(paiement.getMontant());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setStatut(paiement.getStatut() != null ? paiement.getStatut().name() : null);
        dto.setMode(paiement.getMode() != null ? paiement.getMode().name() : null);

        if (paiement.getCommande() != null) {
            dto.setCommande(new CommandeMapper().toResponseDTO(paiement.getCommande()));
        }

        return dto;
    }

    public Paiement toEntity(PaiementRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Paiement paiement = new Paiement();
        paiement.setMontant(dto.getMontant());
        paiement.setDatePaiement(dto.getDatePaiement());

        if (dto.getStatut() != null) {
            paiement.setStatut(tn.esprit.refood.offers.entity.StatutPaiement.valueOf(dto.getStatut()));
        }

        if (dto.getMode() != null) {
            paiement.setMode(tn.esprit.refood.offers.entity.ModePaiement.valueOf(dto.getMode()));
        }

        return paiement;
    }

    public void updateEntityFromDTO(PaiementRequestDTO dto, Paiement paiement) {
        if (dto == null || paiement == null) {
            return;
        }

        if (dto.getMontant() != null) {
            paiement.setMontant(dto.getMontant());
        }
        if (dto.getDatePaiement() != null) {
            paiement.setDatePaiement(dto.getDatePaiement());
        }
        if (dto.getStatut() != null) {
            paiement.setStatut(tn.esprit.refood.offers.entity.StatutPaiement.valueOf(dto.getStatut()));
        }
        if (dto.getMode() != null) {
            paiement.setMode(tn.esprit.refood.offers.entity.ModePaiement.valueOf(dto.getMode()));
        }
    }
}
