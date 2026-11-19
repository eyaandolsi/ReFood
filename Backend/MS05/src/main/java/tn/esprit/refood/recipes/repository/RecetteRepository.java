package tn.esprit.refood.recipes.repository;

import tn.esprit.refood.recipes.model.Difficulte;
import tn.esprit.refood.recipes.model.Recette;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetteRepository extends JpaRepository<Recette, Long> {

    List<Recette> findByTitreContainingIgnoreCase(String titre);

    List<Recette> findByDifficulte(Difficulte difficulte);

    List<Recette> findByTempsPreparationLessThanEqual(Integer minutes);
}