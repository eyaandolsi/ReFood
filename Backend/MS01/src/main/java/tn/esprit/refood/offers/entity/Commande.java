package tn.esprit.refood.offers.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entité représentant une commande dans le système REFOOD.
 * 
 * Une commande est créée par un utilisateur pour une offre spécifique.
 * Elle peut avoir plusieurs paiements et suit un workflow de statuts.
 * 
 * @Entity : Indique que cette classe est une entité JPA.
 * @Table : Spécifie le nom de la table (commande).
 */
@Entity
@Table(name = "commande")
public class Commande {

    /**
     * Identifiant unique de la commande.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Date à laquelle la commande a été créée.
     */
    @Column(nullable = false)
    private Date dateCommande;

    /**
     * Montant total de la commande.
     * Correspond au prix de l'offre multiplié par la quantité commandée.
     */
    @Column(nullable = false)
    private Double montantTotal;

    /**
     * Statut actuel de la commande (EN_ATTENTE, CONFIRMEE, ANNULEE, LIVREE).
     * 
     * Le statut suit le workflow:
     * EN_ATTENTE -> CONFIRMEE -> LIVREE
     * (ou ANNULEE à tout moment)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommande statut;

    /**
     * Offre associée à cette commande.
     * 
     * @ManyToOne : Plusieurs commandes peuvent concerner une offre.
     * @JoinColumn : Clé étrangère offre_id.
     */
    @ManyToOne
    @JoinColumn(name = "offre_id")
    private Offre offre;

    /**
     * Liste des paiements associés à cette commande.
     * 
     * @OneToMany : Une commande peut avoir plusieurs paiements.
     * cascade = CascadeType.ALL : Supprimer une commande supprime aussi ses paiements.
     * fetch = FetchType.LAZY : Les paiements ne sont chargés que si nécessaires.
     */
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paiement> paiements;

    /**
     * Constructeur vide requis par JPA/Hibernate.
     */
    public Commande() {
    }

    /**
     * Constructeur avec les champs principaux.
     */
    public Commande(Long id, Date dateCommande, Double montantTotal, StatutCommande statut) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.statut = statut;
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

    public StatutCommande getStatut() {
        return statut;
    }

    public void setStatut(StatutCommande statut) {
        this.statut = statut;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }
}
