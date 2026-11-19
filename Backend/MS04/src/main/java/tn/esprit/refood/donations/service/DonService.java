package tn.esprit.refood.donations.service;

import tn.esprit.refood.donations.dto.*;
import tn.esprit.refood.donations.entity.*;
import tn.esprit.refood.donations.exception.ConflictException;
import tn.esprit.refood.donations.exception.ForbiddenException;
import tn.esprit.refood.donations.exception.ResourceNotFoundException;
import tn.esprit.refood.donations.repository.DonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DonService {

    private static final List<StatutDon> STATUTS_ACTIFS =
            List.of(StatutDon.DEMANDE, StatutDon.ACCEPTE, StatutDon.PLANIFIE);

    private final DonRepository repository;
    private final AssociationService associationService;

    /** Demander un don (règle : seule une association vérifiée peut demander). */
    public DonResponse create(DonRequest req) {
        Association asso = associationService.getEntity(req.associationId());
        if (!asso.isVerifiee()) {
            throw new ForbiddenException("Seule une association vérifiée peut demander un don");
        }
        if (repository.existsByOffreIdAndAssociationIdAndStatutIn(req.offreId(), asso.getId(), STATUTS_ACTIFS)) {
            throw new ConflictException("Une demande est déjà en cours pour cette offre");
        }
        // TODO semaine 6 : consulter l'offre et réserver la quantité auprès de MS1 (OpenFeign)
        Don don = new Don();
        don.setOffreId(req.offreId());
        don.setAssociation(asso);
        don.setQuantite(req.quantite());
        don.setStatut(StatutDon.DEMANDE);
        don.setDateDemande(LocalDateTime.now());
        tracer(don, null);
        return DonResponse.from(repository.save(don));
    }

    @Transactional(readOnly = true)
    public List<DonResponse> findAll(Long associationId, StatutDon statut) {
        List<Don> dons;
        if (associationId != null) dons = repository.findByAssociationId(associationId);
        else if (statut != null) dons = repository.findByStatut(statut);
        else dons = repository.findAll();
        return dons.stream().map(DonResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DonResponse findById(Long id) {
        return DonResponse.from(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<HistoriqueResponse> historique(Long id) {
        return getEntity(id).getHistorique().stream().map(HistoriqueResponse::from).toList();
    }

    public DonResponse accepter(Long id) {
        Don don = getEntity(id);
        StatutDon ancien = don.getStatut();
        don.accepter();
        tracer(don, ancien);
        return DonResponse.from(don);
    }

    public DonResponse refuser(Long id, String motif) {
        Don don = getEntity(id);
        StatutDon ancien = don.getStatut();
        don.refuser(motif);
        tracer(don, ancien);
        return DonResponse.from(don);
    }

    public DonResponse marquerRecu(Long id) {
        Don don = getEntity(id);
        StatutDon ancien = don.getStatut();
        don.marquerRecu();
        tracer(don, ancien);
        return DonResponse.from(don);
    }

    public DonResponse annuler(Long id) {
        Don don = getEntity(id);
        StatutDon ancien = don.getStatut();
        don.annuler();
        tracer(don, ancien);
        return DonResponse.from(don);
    }

    /** Planifier un créneau de retrait (don ACCEPTE -> PLANIFIE). */
    public DonResponse planifier(Long id, CreneauRequest req) {
        if (!req.dateFin().isAfter(req.dateDebut())) {
            throw new ConflictException("La date de fin doit être postérieure à la date de début");
        }
        Don don = getEntity(id);
        StatutDon ancien = don.getStatut();
        don.planifier();
        don.setCreneau(CreneauRetrait.builder()
                .don(don).dateDebut(req.dateDebut()).dateFin(req.dateFin()).build());
        tracer(don, ancien);
        return DonResponse.from(don);
    }

    public DonResponse confirmerCreneau(Long id) {
        Don don = getEntity(id);
        if (don.getCreneau() == null) {
            throw new ConflictException("Aucun créneau planifié pour ce don");
        }
        don.getCreneau().confirmer();
        return DonResponse.from(don);
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private void tracer(Don don, StatutDon ancien) {
        don.getHistorique().add(HistoriqueDon.builder()
                .don(don).ancienStatut(ancien).nouveauStatut(don.getStatut())
                .dateChangement(LocalDateTime.now()).build());
    }

    private Don getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Don", id));
    }
}
