package tn.esprit.refood.donations.repository;

import tn.esprit.refood.donations.entity.Don;
import tn.esprit.refood.donations.entity.StatutDon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;

public interface DonRepository extends JpaRepository<Don, Long> {
    List<Don> findByAssociationId(Long associationId);
    List<Don> findByStatut(StatutDon statut);
    boolean existsByOffreIdAndAssociationIdAndStatutIn(Long offreId, Long associationId, Collection<StatutDon> statuts);
}
