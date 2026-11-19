package tn.esprit.refood.donations.service;

import tn.esprit.refood.donations.dto.*;
import tn.esprit.refood.donations.entity.Association;
import tn.esprit.refood.donations.exception.ResourceNotFoundException;
import tn.esprit.refood.donations.repository.AssociationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssociationService {

    private final AssociationRepository repository;

    public AssociationResponse create(AssociationRequest req) {
        Association a = Association.builder()
                .nom(req.nom()).adresse(req.adresse()).telephone(req.telephone()).build();
        return AssociationResponse.from(repository.save(a));
    }

    @Transactional(readOnly = true)
    public List<AssociationResponse> findAll() {
        return repository.findAll().stream().map(AssociationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AssociationResponse findById(Long id) {
        return AssociationResponse.from(getEntity(id));
    }

    public AssociationResponse update(Long id, AssociationRequest req) {
        Association a = getEntity(id);
        a.setNom(req.nom());
        a.setAdresse(req.adresse());
        a.setTelephone(req.telephone());
        return AssociationResponse.from(a);
    }

    public AssociationResponse verifier(Long id) {
        Association a = getEntity(id);
        a.verifier();
        return AssociationResponse.from(a);
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    public Association getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Association", id));
    }
}
