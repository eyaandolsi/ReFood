package tn.esprit.refood.offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.refood.offers.entity.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
