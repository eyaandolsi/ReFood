package tn.esprit.refood.recipes.repository;

import tn.esprit.refood.recipes.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByNomIgnoreCase(String nom);

    List<Ingredient> findByNomContainingIgnoreCase(String nom);
}