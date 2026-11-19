package tn.esprit.refood.offers.dto;

/**
 * DTO pour la réponse d'une catégorie.
 */
public class CategorieResponseDTO {

    private Long id;
    private String nom;
    private String description;

    public CategorieResponseDTO() {
    }

    public CategorieResponseDTO(Long id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
