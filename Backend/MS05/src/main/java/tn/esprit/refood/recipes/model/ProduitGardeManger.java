package tn.esprit.refood.recipes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "produit_garde_manger")
public class ProduitGardeManger {

    /** Nombre de jours avant peremption a partir duquel un produit est "proche". */
    public static final int SEUIL_JOURS_PEREMPTION = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "utilisateur_id", nullable = false)
    private String utilisateurId;

    @NotBlank
    @Column(nullable = false)
    private String nom;

    private LocalDate datePeremption;

    @Column(nullable = false)
    private LocalDateTime ajouteLe;

    // ProduitGardeManger 0..* --- 0..1 Ingredient  ("correspond a")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public ProduitGardeManger() {
    }

    @PrePersist
    void avantCreation() {
        if (ajouteLe == null) {
            ajouteLe = LocalDateTime.now();
        }
    }

    public boolean estProchePeremption() {
        if (datePeremption == null) {
            return false;
        }
        long jours = ChronoUnit.DAYS.between(LocalDate.now(), datePeremption);
        return jours <= SEUIL_JOURS_PEREMPTION;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(String utilisateurId) { this.utilisateurId = utilisateurId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDate getDatePeremption() { return datePeremption; }
    public void setDatePeremption(LocalDate datePeremption) { this.datePeremption = datePeremption; }

    public LocalDateTime getAjouteLe() { return ajouteLe; }
    public void setAjouteLe(LocalDateTime ajouteLe) { this.ajouteLe = ajouteLe; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }
}