package tn.esprit.refood.events.service;

import tn.esprit.refood.events.domain.Evenement;
import tn.esprit.refood.events.domain.Inscription;
import tn.esprit.refood.events.domain.StatutEvenement;
import tn.esprit.refood.events.domain.StatutInscription;
import tn.esprit.refood.events.domain.TypeEvenement;
import tn.esprit.refood.events.repository.EvenementRepository;
import tn.esprit.refood.events.repository.InscriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EvenementService {
    private final EvenementRepository repository;
    private final InscriptionRepository inscriptionRepository;

    public EvenementService(EvenementRepository repository,
                            InscriptionRepository inscriptionRepository) {
        this.repository = repository;
        this.inscriptionRepository = inscriptionRepository;
    }

    public Evenement create(Evenement evenement) {
        validate(evenement);
        evenement.setStatut(StatutEvenement.BROUILLON);
        return repository.save(evenement);
    }

    @Transactional(readOnly = true)
    public List<Evenement> findAll(TypeEvenement type, StatutEvenement statut) {
        if (type != null && statut != null) return repository.findByTypeAndStatut(type, statut);
        if (type != null) return repository.findByType(type);
        if (statut != null) return repository.findByStatut(statut);
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Evenement findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evenement introuvable"));
    }

    public Evenement update(Long id, Evenement values) {
        Evenement evenement = findById(id);
        validate(values);
        evenement.setTitre(values.getTitre());
        evenement.setType(values.getType());
        evenement.setAssociationId(values.getAssociationId());
        evenement.setDescription(values.getDescription());
        evenement.setLieu(values.getLieu());
        evenement.setDateDebut(values.getDateDebut());
        evenement.setDateFin(values.getDateFin());
        evenement.setCapacite(values.getCapacite());
        evenement.setOrganisateurId(values.getOrganisateurId());
        return repository.save(evenement);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public Evenement publier(Long id) {
        Evenement evenement = findById(id);
        evenement.publier();
        return repository.save(evenement);
    }

    public Evenement annuler(Long id) {
        Evenement evenement = findById(id);
        evenement.annuler();
        return repository.save(evenement);
    }

    public Evenement cloturer(Long id) {
        Evenement evenement = findById(id);
        evenement.cloturer();
        return repository.save(evenement);
    }

    public Inscription inscrire(Long evenementId, String participantId) {
        Evenement evenement = findById(evenementId);
        if (evenement.getStatut() == StatutEvenement.ANNULE
                || evenement.getStatut() == StatutEvenement.TERMINE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Les inscriptions sont fermees");
        }
        if (evenement.getInscriptions().stream()
                .anyMatch(i -> i.getParticipantId().equals(participantId)
                        && i.getStatut() != StatutInscription.ANNULEE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Participant deja inscrit");
        }

        Inscription inscription = new Inscription();
        inscription.setParticipantId(participantId);
        inscription.setDateInscription(LocalDateTime.now());
        long places = evenement.getInscriptions().stream()
                .filter(i -> i.getStatut() == StatutInscription.INSCRIT).count();
        inscription.setStatut(places >= evenement.getCapacite()
                ? StatutInscription.EN_ATTENTE : StatutInscription.INSCRIT);
        evenement.addInscription(inscription);
        evenement.actualiserStatutCapacite();
        repository.save(evenement);
        return inscriptionRepository.saveAndFlush(inscription);
    }

    public void annulerInscription(Long evenementId, String participantId) {
        Evenement evenement = findById(evenementId);
        Inscription inscription = evenement.getInscriptions().stream()
                .filter(i -> i.getParticipantId().equals(participantId)
                        && i.getStatut() != StatutInscription.ANNULEE)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscription introuvable"));
        inscription.annuler();
        if (evenement.getStatut() == StatutEvenement.COMPLET) {
            evenement.setStatut(StatutEvenement.OUVERT);
        }
        repository.save(evenement);
    }

    private void validate(Evenement evenement) {
        if (evenement.getTitre() == null || evenement.getTitre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le titre est obligatoire");
        }
        if (evenement.getCapacite() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La capacite doit etre superieure a zero");
        }
        if (evenement.getDateDebut() == null || evenement.getDateFin() == null
                || !evenement.getDateFin().isAfter(evenement.getDateDebut())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les dates de l'evenement sont invalides");
        }
    }
}
