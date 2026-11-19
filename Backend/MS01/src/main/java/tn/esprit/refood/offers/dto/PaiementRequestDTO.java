package tn.esprit.refood.offers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Date;

public class PaiementRequestDTO {

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double montant;

    private Date datePaiement;

    private String statut;

    private String mode;

    private Long commandeId;

    public PaiementRequestDTO() {
    }

    public PaiementRequestDTO(Double montant, Date datePaiement, String statut, String mode, Long commandeId) {
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statut = statut;
        this.mode = mode;
        this.commandeId = commandeId;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }
}
