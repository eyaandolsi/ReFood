package tn.esprit.refood.offers.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entité représentant une offre dans le système REFOOD.
 * 
 * Une offre représente un produit ou service disponible pour les commandes.
 * Elle est liée à une catégorie et peut avoir plusieurs commandes.
 * 
 * @Entity : Indique que cette classe est une entité JPA mappée à une table de base de données.
 * @Table : Spécifie le nom de la table en base de données (offre).
 */
@Entity
@Table(name = "offre")
public class Offre {

    /**
     * Identifiant unique de l'offre.
     * 
     * @Id : Marque ce champ comme clé primaire.
     * @GeneratedValue : Indique que la valeur est générée automatiquement par la base de données.
     * GenerationType.IDENTITY : Utilise la colonne AUTO_INCREMENT de MySQL.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titre de l'offre.
     * nullable = false : Ce champ ne peut pas être null en base de données.
     */
    @Column(nullable = false)
    private String titre;

    /**
     * Description détaillée de l'offre.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Date d'expiration de l'offre.
     * name = "date_expiration" : Nom de la colonne en base de données (snake_case).
     */
    @Column(name = "date_expiration")
    private Date dateExpiration;

    /**
     * Quantité disponible de l'offre.
     * Représente le stock disponible pour les commandes.
     */
    @Column(nullable = false)
    private Integer quantite;

    /**
     * Type de l'offre (ALIMENTAIRE ou NON_ALIMENTAIRE).
     * 
     * @Enumerated(EnumType.STRING) : Stocke l'enum comme chaîne de caractères en base
     *                              plutôt que comme ordinal (plus lisible et plus sûr).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeOffre typeOffre;

    /**
     * Statut actuel de l'offre (DISPONIBLE, RESERVEE, EXPIREE, ANNULEE).
     * 
     * Le statut suit le workflow métier:
     * DISPONIBLE -> RESERVEE -> EXPIREE/ANNULEE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutOffre statutOffre;

    /**
     * Catégorie à laquelle appartient l'offre.
     * 
     * @ManyToOne : Relation plusieurs-à-un. Plusieurs offres peuvent appartenir à une catégorie.
     * @JoinColumn : Spécifie la colonne de clé étrangère (categorie_id).
     */
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    /**
     * Liste des commandes associées à cette offre.
     * 
     * @OneToMany : Relation un-à-plusieurs. Une offre peut avoir plusieurs commandes.
     * mappedBy = "offre" : Indique que le côté propriétaire de la relation est dans l'entité Commande.
     * cascade = CascadeType.ALL : Les opérations sur l'offre se propagent aux commandes
     *                          (ex: supprimer une offre supprime aussi ses commandes).
     * fetch = FetchType.LAZY : Les commandes ne sont chargées que si on y accède
     *                         (optimisation de performance).
     */
    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Commande> commandes;

    /**
     * Constructeur vide requis par JPA/Hibernate.
     */
    public Offre() {
    }

    /**
     * Constructeur avec tous les champs.
     * Utile pour les tests ou la création d'offres programmées.
     */
    public Offre(Long id, String titre, String description, Date dateExpiration, Integer quantite, TypeOffre typeOffre, StatutOffre statutOffre) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateExpiration = dateExpiration;
        this.quantite = quantite;
        this.typeOffre = typeOffre;
        this.statutOffre = statutOffre;
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

    public TypeOffre getTypeOffre() {
        return typeOffre;
    }

    public void setTypeOffre(TypeOffre typeOffre) {
        this.typeOffre = typeOffre;
    }

    public StatutOffre getStatutOffre() {
        return statutOffre;
    }

    public void setStatutOffre(StatutOffre statutOffre) {
        this.statutOffre = statutOffre;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }
}
