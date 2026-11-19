package tn.esprit.refood.offers.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour la création et modification d'une catégorie.
 */
public class CategorieRequestDTO {

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    private String nom;

    private String description;

    public CategorieRequestDTO() {
    }

    public CategorieRequestDTO(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
