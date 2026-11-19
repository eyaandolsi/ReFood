package tn.esprit.refood.recipes.service;

import tn.esprit.refood.recipes.exception.ResourceNotFoundException;
import tn.esprit.refood.recipes.model.Ingredient;
import tn.esprit.refood.recipes.model.ProduitGardeManger;
import tn.esprit.refood.recipes.repository.ProduitGardeMangerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Copie locale du garde-manger de l'utilisateur, utilisee pour recommander des recettes.
 * A terme, ces donnees peuvent etre synchronisees depuis le microservice "garde-manger" de ton equipe.
 */
@Service
@Transactional
public class ProduitGardeMangerService {

    private final ProduitGardeMangerRepository repository;
    private final IngredientService ingredientService;

    public ProduitGardeMangerService(ProduitGardeMangerRepository repository,
                                     IngredientService ingredientService) {
        this.repository = repository;
        this.ingredientService = ingredientService;
    }

    @Transactional(readOnly = true)
    public List<ProduitGardeManger> listerParUtilisateur(String utilisateurId) {
        return repository.findByUtilisateurIdOrderByDatePeremptionAsc(utilisateurId);
    }

    @Transactional(readOnly = true)
    public List<ProduitGardeManger> listerProchesPeremption(String utilisateurId) {
        LocalDate limite = LocalDate.now().plusDays(ProduitGardeManger.SEUIL_JOURS_PEREMPTION);
        return repository.findByUtilisateurIdAndDatePeremptionLessThanEqual(utilisateurId, limite);
    }

    public ProduitGardeManger ajouter(ProduitGardeManger produit) {
        produit.setId(null);
        rattacherIngredient(produit);
        return repository.save(produit);
    }

    public ProduitGardeManger modifier(Long id, ProduitGardeManger donnees) {
        ProduitGardeManger existant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable : " + id));
        existant.setNom(donnees.getNom());
        existant.setDatePeremption(donnees.getDatePeremption());
        existant.setIngredient(donnees.getIngredient());
        rattacherIngredient(existant);
        return repository.save(existant);
    }

    public void supprimer(Long id) {
        ProduitGardeManger produit = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable : " + id));
        repository.delete(produit);
    }

    private void rattacherIngredient(ProduitGardeManger produit) {
        Ingredient ing = produit.getIngredient();
        if (ing != null && ing.getId() != null) {
            produit.setIngredient(ingredientService.trouver(ing.getId()));
        } else {
            produit.setIngredient(null);
        }
    }
}