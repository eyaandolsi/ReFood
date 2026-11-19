package tn.esprit.refood.offers.dto;

import java.util.Date;

public class PaiementResponseDTO {

    private Long id;
    private Double montant;
    private Date datePaiement;
    private String statut;
    private String mode;
    private CommandeResponseDTO commande;

    public PaiementResponseDTO() {
    }

    public PaiementResponseDTO(Long id, Double montant, Date datePaiement, String statut, String mode, CommandeResponseDTO commande) {
        this.id = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statut = statut;
        this.mode = mode;
        this.commande = commande;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CommandeResponseDTO getCommande() {
        return commande;
    }

    public void setCommande(CommandeResponseDTO commande) {
        this.commande = commande;
    }
}
