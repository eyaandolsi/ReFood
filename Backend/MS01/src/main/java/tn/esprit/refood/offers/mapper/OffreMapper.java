package tn.esprit.refood.offers.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.refood.offers.dto.OffreRequestDTO;
import tn.esprit.refood.offers.dto.OffreResponseDTO;
import tn.esprit.refood.offers.entity.Offre;

/**
 * Mapper pour convertir entre Entity et DTO pour Offre.
 */
@Component
public class OffreMapper {

    public OffreResponseDTO toResponseDTO(Offre offre) {
        if (offre == null) {
            return null;
        }

        OffreResponseDTO dto = new OffreResponseDTO();
        dto.setId(offre.getId());
        dto.setTitre(offre.getTitre());
        dto.setDescription(offre.getDescription());
        dto.setDateExpiration(offre.getDateExpiration());
        dto.setQuantite(offre.getQuantite());
        dto.setTypeOffre(offre.getTypeOffre() != null ? offre.getTypeOffre().name() : null);
        dto.setStatutOffre(offre.getStatutOffre() != null ? offre.getStatutOffre().name() : null);

        if (offre.getCategorie() != null) {
            dto.setCategorie(new CategorieMapper().toResponseDTO(offre.getCategorie()));
        }

        return dto;
    }

    public Offre toEntity(OffreRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Offre offre = new Offre();
        offre.setTitre(dto.getTitre());
        offre.setDescription(dto.getDescription());
        offre.setDateExpiration(dto.getDateExpiration());
        offre.setQuantite(dto.getQuantite());

        if (dto.getTypeOffre() != null) {
            offre.setTypeOffre(tn.esprit.refood.offers.entity.TypeOffre.valueOf(dto.getTypeOffre()));
        }

        if (dto.getStatutOffre() != null) {
            offre.setStatutOffre(tn.esprit.refood.offers.entity.StatutOffre.valueOf(dto.getStatutOffre()));
        }

        return offre;
    }

    public void updateEntityFromDTO(OffreRequestDTO dto, Offre offre) {
        if (dto == null || offre == null) {
            return;
        }

        if (dto.getTitre() != null) {
            offre.setTitre(dto.getTitre());
        }
        if (dto.getDescription() != null) {
            offre.setDescription(dto.getDescription());
        }
        if (dto.getDateExpiration() != null) {
            offre.setDateExpiration(dto.getDateExpiration());
        }
        if (dto.getQuantite() != null) {
            offre.setQuantite(dto.getQuantite());
        }
        if (dto.getTypeOffre() != null) {
            offre.setTypeOffre(tn.esprit.refood.offers.entity.TypeOffre.valueOf(dto.getTypeOffre()));
        }
        if (dto.getStatutOffre() != null) {
            offre.setStatutOffre(tn.esprit.refood.offers.entity.StatutOffre.valueOf(dto.getStatutOffre()));
        }
    }
}
