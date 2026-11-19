package tn.esprit.refood.recipes.repository;

import tn.esprit.refood.recipes.model.Favori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriRepository extends JpaRepository<Favori, Long> {

    List<Favori> findByUtilisateurIdOrderBySauvegardeLeDesc(String utilisateurId);

    Optional<Favori> findByUtilisateurIdAndRecetteId(String utilisateurId, Long recetteId);

    boolean existsByUtilisateurIdAndRecetteId(String utilisateurId, Long recetteId);
}