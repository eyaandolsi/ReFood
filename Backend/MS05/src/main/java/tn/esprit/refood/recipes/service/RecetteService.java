package tn.esprit.refood.recipes.service;

import tn.esprit.refood.recipes.exception.ResourceNotFoundException;
import tn.esprit.refood.recipes.model.Difficulte;
import tn.esprit.refood.recipes.model.IngredientRecette;
import tn.esprit.refood.recipes.model.ProduitGardeManger;
import tn.esprit.refood.recipes.model.Recette;
import tn.esprit.refood.recipes.repository.IngredientRepository;
import tn.esprit.refood.recipes.repository.ProduitGardeMangerRepository;
import tn.esprit.refood.recipes.repository.RecetteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecetteService {

    private final RecetteRepository recetteRepository;
    private final IngredientRepository ingredientRepository;
    private final ProduitGardeMangerRepository produitRepository;

    public RecetteService(RecetteRepository recetteRepository,
                          IngredientRepository ingredientRepository,
                          ProduitGardeMangerRepository produitRepository) {
        this.recetteRepository = recetteRepository;
        this.ingredientRepository = ingredientRepository;
        this.produitRepository = produitRepository;
    }

    @Transactional(readOnly = true)
    public List<Recette> lister(String titre, Difficulte difficulte, Integer tempsMax) {
        if (titre != null && !titre.isBlank()) {
            return recetteRepository.findByTitreContainingIgnoreCase(titre);
        }
        if (difficulte != null) {
            return recetteRepository.findByDifficulte(difficulte);
        }
        if (tempsMax != null) {
            return recetteRepository.findByTempsPreparationLessThanEqual(tempsMax);
        }
        return recetteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Recette trouver(Long id) {
        return recetteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recette introuvable : " + id));
    }

    public Recette creer(Recette recette) {
        recette.setId(null);
        resoudreIngredients(recette);
        return recetteRepository.save(recette);
    }

    public Recette modifier(Long id, Recette donnees) {
        Recette existante = trouver(id);
        existante.setTitre(donnees.getTitre());
        existante.setInstructions(donnees.getInstructions());
        existante.setTempsPreparation(donnees.getTempsPreparation());
        existante.setDifficulte(donnees.getDifficulte());
        existante.setUrlImage(donnees.getUrlImage());
        existante.setIngredients(donnees.getIngredients());
        resoudreIngredients(existante);
        return recetteRepository.save(existante);
    }

    public void supprimer(Long id) {
        recetteRepository.delete(trouver(id));
    }

    /**
     * Recommande des recettes a partir du garde-manger de l'utilisateur.
     * Les produits proches de la peremption sont prioritaires dans le calcul.
     * Retourne les recettes triees par score decroissant (score > 0 uniquement).
     */
    @Transactional(readOnly = true)
    public List<RecetteScore> recommander(String utilisateurId, boolean prioriteAntiGaspi) {
        List<ProduitGardeManger> produits =
                produitRepository.findByUtilisateurIdOrderByDatePeremptionAsc(utilisateurId);

        Set<Long> ingredientIds = produits.stream()
                .filter(p -> p.getIngredient() != null)
                .filter(p -> !prioriteAntiGaspi || p.estProchePeremption())
                .map(p -> p.getIngredient().getId())
                .collect(Collectors.toSet());

        return recetteRepository.findAll().stream()
                .map(r -> new RecetteScore(r, r.scoreCorrespondance(ingredientIds)))
                .filter(rs -> rs.score() > 0)
                .sorted(Comparator.comparingInt(RecetteScore::score).reversed())
                .toList();
    }

    /** Recette + son score de correspondance (0-100). */
    public record RecetteScore(Recette recette, int score) {
    }

    /** Rattache chaque IngredientRecette a un Ingredient existant en base (par id). */
    private void resoudreIngredients(Recette recette) {
        for (IngredientRecette ir : recette.getIngredients()) {
            if (ir.getIngredient() == null || ir.getIngredient().getId() == null) {
                throw new IllegalArgumentException("Chaque ingredient de la recette doit contenir l'id d'un ingredient existant");
            }
            Long ingredientId = ir.getIngredient().getId();
            ir.setIngredient(ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient introuvable : " + ingredientId)));
            ir.setRecette(recette);
        }
    }
}