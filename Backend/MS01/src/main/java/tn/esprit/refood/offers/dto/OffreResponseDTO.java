package tn.esprit.refood.offers.dto;

import java.util.Date;

/**
 * DTO pour la réponse d'une offre.
 * Expose uniquement les données nécessaires au client.
 */
public class OffreResponseDTO {

    private Long id;
    private String titre;
    private String description;
    private Date dateExpiration;
    private Integer quantite;
    private String typeOffre;
    private String statutOffre;
    private CategorieResponseDTO categorie;

    public OffreResponseDTO() {
    }

    public OffreResponseDTO(Long id, String titre, String description, Date dateExpiration, Integer quantite, String typeOffre, String statutOffre, CategorieResponseDTO categorie) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateExpiration = dateExpiration;
        this.quantite = quantite;
        this.typeOffre = typeOffre;
        this.statutOffre = statutOffre;
        this.categorie = categorie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CategorieResponseDTO getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieResponseDTO categorie) {
        this.categorie = categorie;
    }
}
