package tn.esprit.refood.events.repository;

import tn.esprit.refood.events.domain.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    List<Inscription> findByEvenementId(Long evenementId);
}
