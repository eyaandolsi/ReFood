package tn.esprit.refood.offers.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.refood.offers.dto.CategorieRequestDTO;
import tn.esprit.refood.offers.dto.CategorieResponseDTO;
import tn.esprit.refood.offers.entity.Categorie;

/**
 * Mapper pour convertir entre Entity et DTO pour Categorie.
 */
@Component
public class CategorieMapper {

    public CategorieResponseDTO toResponseDTO(Categorie categorie) {
        if (categorie == null) {
            return null;
        }

        CategorieResponseDTO dto = new CategorieResponseDTO();
        dto.setId(categorie.getId());
        dto.setNom(categorie.getNom());
        dto.setDescription(categorie.getDescription());

        return dto;
    }

    public Categorie toEntity(CategorieRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Categorie categorie = new Categorie();
        categorie.setNom(dto.getNom());
        categorie.setDescription(dto.getDescription());

        return categorie;
    }

    public void updateEntityFromDTO(CategorieRequestDTO dto, Categorie categorie) {
        if (dto == null || categorie == null) {
            return;
        }

        if (dto.getNom() != null) {
            categorie.setNom(dto.getNom());
        }
        if (dto.getDescription() != null) {
            categorie.setDescription(dto.getDescription());
        }
    }
}
