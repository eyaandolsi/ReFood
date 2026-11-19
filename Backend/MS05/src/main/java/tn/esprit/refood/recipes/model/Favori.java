package tn.esprit.refood.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@Table(name = "favori",
        uniqueConstraints = @UniqueConstraint(columnNames = {"utilisateur_id", "recette_id"}))
public class Favori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Id de l'utilisateur (gere par le microservice utilisateurs d'un(e) ami(e)). */
    @Column(name = "utilisateur_id", nullable = false)
    private String utilisateurId;

    /** Note de 1 a 5 (optionnelle). */
    @Min(1)
    @Max(5)
    private Integer note;

    @Column(nullable = false)
    private LocalDateTime sauvegardeLe;

    @JsonIgnoreProperties("ingredients")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "recette_id", nullable = false)
    private Recette recette;

    public Favori() {
    }

    @PrePersist
    void avantCreation() {
        if (sauvegardeLe == null) {
            sauvegardeLe = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(String utilisateurId) { this.utilisateurId = utilisateurId; }

    public Integer getNote() { return note; }
    public void setNote(Integer note) { this.note = note; }

    public LocalDateTime getSauvegardeLe() { return sauvegardeLe; }
    public void setSauvegardeLe(LocalDateTime sauvegardeLe) { this.sauvegardeLe = sauvegardeLe; }

    public Recette getRecette() { return recette; }
    public void setRecette(Recette recette) { this.recette = recette; }
}