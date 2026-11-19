package tn.esprit.refood.donations.repository;

import tn.esprit.refood.donations.entity.Besoin;
import tn.esprit.refood.donations.entity.Urgence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BesoinRepository extends JpaRepository<Besoin, Long> {
    List<Besoin> findByAssociationId(Long associationId);
    List<Besoin> findByUrgence(Urgence urgence);
}
