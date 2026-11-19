package tn.esprit.refood.events.repository;

import tn.esprit.refood.events.domain.Evenement;
import tn.esprit.refood.events.domain.StatutEvenement;
import tn.esprit.refood.events.domain.TypeEvenement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    List<Evenement> findByTypeAndStatut(TypeEvenement type, StatutEvenement statut);
    List<Evenement> findByType(TypeEvenement type);
    List<Evenement> findByStatut(StatutEvenement statut);
}
