package tn.esprit.refood.donations.service;

import tn.esprit.refood.donations.dto.*;
import tn.esprit.refood.donations.entity.Besoin;
import tn.esprit.refood.donations.exception.ResourceNotFoundException;
import tn.esprit.refood.donations.repository.BesoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BesoinService {

    private final BesoinRepository repository;
    private final AssociationService associationService;

    public BesoinResponse create(BesoinRequest req) {
        Besoin b = new Besoin();
        apply(b, req);
        return BesoinResponse.from(repository.save(b));
    }

    @Transactional(readOnly = true)
    public List<BesoinResponse> findAll(Long associationId) {
        List<Besoin> besoins = associationId == null
                ? repository.findAll() : repository.findByAssociationId(associationId);
        return besoins.stream().map(BesoinResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public BesoinResponse findById(Long id) {
        return BesoinResponse.from(getEntity(id));
    }

    public BesoinResponse update(Long id, BesoinRequest req) {
        Besoin b = getEntity(id);
        apply(b, req);
        return BesoinResponse.from(b);
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private void apply(Besoin b, BesoinRequest req) {
        b.setAssociation(associationService.getEntity(req.associationId()));
        b.setCategorie(req.categorie());
        b.setQuantite(req.quantite());
        b.setUrgence(req.urgence());
        b.setDateLimite(req.dateLimite());
    }

    private Besoin getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Besoin", id));
    }
}
