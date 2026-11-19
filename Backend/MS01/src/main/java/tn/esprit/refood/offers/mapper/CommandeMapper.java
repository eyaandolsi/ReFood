package tn.esprit.refood.offers.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.refood.offers.dto.CommandeRequestDTO;
import tn.esprit.refood.offers.dto.CommandeResponseDTO;
import tn.esprit.refood.offers.entity.Commande;

/**
 * Mapper pour convertir entre Entity et DTO pour Commande.
 */
@Component
public class CommandeMapper {

    public CommandeResponseDTO toResponseDTO(Commande commande) {
        if (commande == null) {
            return null;
        }

        CommandeResponseDTO dto = new CommandeResponseDTO();
        dto.setId(commande.getId());
        dto.setDateCommande(commande.getDateCommande());
        dto.setMontantTotal(commande.getMontantTotal());
        dto.setStatut(commande.getStatut() != null ? commande.getStatut().name() : null);

        if (commande.getOffre() != null) {
            dto.setOffre(new OffreMapper().toResponseDTO(commande.getOffre()));
        }

        return dto;
    }

    public Commande toEntity(CommandeRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Commande commande = new Commande();
        commande.setDateCommande(dto.getDateCommande());
        commande.setMontantTotal(dto.getMontantTotal());

        if (dto.getStatut() != null) {
            commande.setStatut(tn.esprit.refood.offers.entity.StatutCommande.valueOf(dto.getStatut()));
        }

        return commande;
    }

    public void updateEntityFromDTO(CommandeRequestDTO dto, Commande commande) {
        if (dto == null || commande == null) {
            return;
        }

        if (dto.getDateCommande() != null) {
            commande.setDateCommande(dto.getDateCommande());
        }
        if (dto.getMontantTotal() != null) {
            commande.setMontantTotal(dto.getMontantTotal());
        }
        if (dto.getStatut() != null) {
            commande.setStatut(tn.esprit.refood.offers.entity.StatutCommande.valueOf(dto.getStatut()));
        }
    }
}
