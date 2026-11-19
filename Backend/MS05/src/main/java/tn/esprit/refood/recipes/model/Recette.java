package tn.esprit.refood.recipes.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tn.esprit.refood.recipes.model.Difficulte;
import tn.esprit.refood.recipes.model.IngredientRecette;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recette")
public class Recette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titre;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String instructions;

    /** Temps de preparation en minutes. */
    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer tempsPreparation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulte difficulte;

    private String urlImage;

    // Recette 1 --- 1..* IngredientRecette  ("contient")
    @Valid
    @NotEmpty
    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<IngredientRecette> ingredients = new ArrayList<>();

    public Recette() {
    }

    /**
     * Score de correspondance (0 a 100) : pourcentage des ingredients de la recette
     * que l'utilisateur possede deja (ids d'ingredients de son garde-manger).
     */
    public int scoreCorrespondance(Collection<Long> ingredientIdsDisponibles) {
        if (ingredients == null || ingredients.isEmpty() || ingredientIdsDisponibles == null) {
            return 0;
        }
        Set<Long> disponibles = Set.copyOf(ingredientIdsDisponibles);
        long correspondants = ingredients.stream()
                .map(ir -> ir.getIngredient().getId())
                .filter(disponibles::contains)
                .count();
        return (int) Math.round(100.0 * correspondants / ingredients.size());
    }

    public void addIngredient(IngredientRecette ir) {
        ir.setRecette(this);
        ingredients.add(ir);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public Integer getTempsPreparation() { return tempsPreparation; }
    public void setTempsPreparation(Integer tempsPreparation) { this.tempsPreparation = tempsPreparation; }

    public Difficulte getDifficulte() { return difficulte; }
    public void setDifficulte(Difficulte difficulte) { this.difficulte = difficulte; }

    public String getUrlImage() { return urlImage; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }

    public List<IngredientRecette> getIngredients() { return ingredients; }
    public void setIngredients(List<IngredientRecette> ingredients) {
        this.ingredients.clear();
        if (ingredients != null) {
            ingredients.forEach(this::addIngredient);
        }
    }
}