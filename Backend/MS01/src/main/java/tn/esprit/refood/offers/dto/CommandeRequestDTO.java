package tn.esprit.refood.offers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Date;

public class CommandeRequestDTO {

    @NotNull(message = "La date de commande est obligatoire")
    private Date dateCommande;

    @NotNull(message = "Le montant total est obligatoire")
    @Positive(message = "Le montant total doit être positif")
    private Double montantTotal;

    private String statut;

    private Long offreId;

    public CommandeRequestDTO() {
    }

    public CommandeRequestDTO(Date dateCommande, Double montantTotal, String statut, Long offreId) {
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.offreId = offreId;
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

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }
}
