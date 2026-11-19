package tn.esprit.refood.offers.dto;

import java.util.Date;

public class CommandeResponseDTO {

    private Long id;
    private Date dateCommande;
    private Double montantTotal;
    private String statut;
    private OffreResponseDTO offre;

    public CommandeResponseDTO() {
    }

    public CommandeResponseDTO(Long id, Date dateCommande, Double montantTotal, String statut, OffreResponseDTO offre) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.offre = offre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public OffreResponseDTO getOffre() {
        return offre;
    }

    public void setOffre(OffreResponseDTO offre) {
        this.offre = offre;
    }
}
