package tn.esprit.refood.offers.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entité représentant une catégorie dans le système REFOOD.
 * 
 * Une catégorie regroupe plusieurs offres similaires.
 * Le nom de la catégorie est unique pour éviter les doublons.
 * 
 * @Entity : Indique que cette classe est une entité JPA.
 * @Table : Spécifie le nom de la table (categorie).
 */
@Entity
@Table(name = "categorie")
public class Categorie {

    /**
     * Identifiant unique de la catégorie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de la catégorie.
     * 
     * nullable = false : Le nom ne peut pas être null.
     * unique = true : Le nom doit être unique (pas deux catégories avec le même nom).
     */
    @Column(nullable = false, unique = true)
    private String nom;

    /**
     * Description de la catégorie.
     * Peut être null si non renseignée.
     */
    @Column
    private String description;

    /**
     * Liste des offres appartenant à cette catégorie.
     * 
     * @OneToMany : Une catégorie peut avoir plusieurs offres.
     * mappedBy = "categorie" : Le côté propriétaire est dans l'entité Offre.
     * cascade = CascadeType.ALL : Supprimer une catégorie supprime aussi ses offres.
     * fetch = FetchType.LAZY : Les offres ne sont chargées que si nécessaires.
     */
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Offre> offres;

    /**
     * Constructeur vide requis par JPA/Hibernate.
     */
    public Categorie() {
    }

    /**
     * Constructeur avec les champs principaux.
     */
    public Categorie(Long id, String nom, String description) {
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

    public List<Offre> getOffres() {
        return offres;
    }

    public void setOffres(List<Offre> offres) {
        this.offres = offres;
    }
}
