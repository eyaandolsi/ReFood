package tn.esprit.refood.recipes.service;

import tn.esprit.refood.recipes.exception.ResourceNotFoundException;
import tn.esprit.refood.recipes.model.Ingredient;
import tn.esprit.refood.recipes.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Ingredient> lister(String recherche) {
        if (recherche != null && !recherche.isBlank()) {
            return repository.findByNomContainingIgnoreCase(recherche);
        }
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Ingredient trouver(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient introuvable : " + id));
    }

    public Ingredient creer(Ingredient ingredient) {
        ingredient.setId(null);
        return repository.save(ingredient);
    }

    public Ingredient modifier(Long id, Ingredient donnees) {
        Ingredient existant = trouver(id);
        existant.setNom(donnees.getNom());
        existant.setCategorie(donnees.getCategorie());
        return repository.save(existant);
    }

    public void supprimer(Long id) {
        repository.delete(trouver(id));
    }
}