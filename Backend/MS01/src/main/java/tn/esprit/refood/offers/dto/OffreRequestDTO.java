package tn.esprit.refood.offers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Date;

/**
 * DTO pour la création et modification d'une offre.
 * Utilise les annotations de validation Jakarta Bean Validation.
 */
public class OffreRequestDTO {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    private Date dateExpiration;

    @NotNull(message = "La quantité est obligatoire")
    @PositiveOrZero(message = "La quantité ne peut pas être négative")
    private Integer quantite;

    private String typeOffre; // OFFRE_GRATUITE ou OFFRE_PAYANTE

    private String statutOffre; // DISPONIBLE, RESERVEE, EXPIREE, ANNULEE

    private Long categorieId;

    public OffreRequestDTO() {
    }

    public OffreRequestDTO(String titre, String description, Date dateExpiration, Integer quantite, String typeOffre, String statutOffre, Long categorieId) {
        this.titre = titre;
        this.description = description;
        this.dateExpiration = dateExpiration;
        this.quantite = quantite;
        this.typeOffre = typeOffre;
        this.statutOffre = statutOffre;
        this.categorieId = categorieId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getTypeOffre() {
        return typeOffre;
    }

    public void setTypeOffre(String typeOffre) {
        this.typeOffre = typeOffre;
    }

    public String getStatutOffre() {
        return statutOffre;
    }

    public void setStatutOffre(String statutOffre) {
        this.statutOffre = statutOffre;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }
}
