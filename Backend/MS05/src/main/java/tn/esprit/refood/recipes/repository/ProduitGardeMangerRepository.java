package tn.esprit.refood.recipes.repository;

import tn.esprit.refood.recipes.model.ProduitGardeManger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProduitGardeMangerRepository extends JpaRepository<ProduitGardeManger, Long> {

    List<ProduitGardeManger> findByUtilisateurIdOrderByDatePeremptionAsc(String utilisateurId);

    List<ProduitGardeManger> findByUtilisateurIdAndDatePeremptionLessThanEqual(String utilisateurId, LocalDate date);
}