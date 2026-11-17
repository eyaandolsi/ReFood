package tn.esprit.refood.offers.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entité représentant un paiement dans le système REFOOD.
 * 
 * Un paiement est associé à une commande et représente
 * le règlement financier de cette commande.
 * 
 * @Entity : Indique que cette classe est une entité JPA.
 * @Table : Spécifie le nom de la table (paiement).
 */
@Entity
@Table(name = "paiement")
public class Paiement {

    /**
     * Identifiant unique du paiement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Montant du paiement.
     * Doit correspondre au montant total de la commande.
     */
    @Column(nullable = false)
    private Double montant;

    /**
     * Date à laquelle le paiement a été effectué.
     * name = "date_paiement" : Nom de la colonne en snake_case.
     */
    @Column(name = "date_paiement")
    private Date datePaiement;

    /**
     * Statut du paiement (EN_ATTENTE, VALIDE, REFUSE).
     * 
     * Le statut suit le workflow:
     * EN_ATTENTE -> VALIDE ou REFUSE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPaiement statut;

    /**
     * Mode de paiement utilisé (CARTE_BANCAIRE, CHEQUE, ESPECES).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModePaiement mode;

    /**
     * Commande associée à ce paiement.
     * 
     * @ManyToOne : Plusieurs paiements peuvent être liés à une commande.
     * @JoinColumn : Clé étrangère commande_id.
     */
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    /**
     * Constructeur vide requis par JPA/Hibernate.
     */
    public Paiement() {
    }

    /**
     * Constructeur avec les champs principaux.
     */
    public Paiement(Long id, Double montant, Date datePaiement, StatutPaiement statut, ModePaiement mode) {
        this.id = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statut = statut;
        this.mode = mode;
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

    public StatutPaiement getStatut() {
        return statut;
    }

    public void setStatut(StatutPaiement statut) {
        this.statut = statut;
    }

    public ModePaiement getMode() {
        return mode;
    }

    public void setMode(ModePaiement mode) {
        this.mode = mode;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}
