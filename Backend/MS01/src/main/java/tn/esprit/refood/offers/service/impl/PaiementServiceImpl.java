package tn.esprit.refood.offers.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.refood.offers.entity.Paiement;
import tn.esprit.refood.offers.exception.BadRequestException;
import tn.esprit.refood.offers.exception.ResourceNotFoundException;
import tn.esprit.refood.offers.repository.PaiementRepository;
import tn.esprit.refood.offers.service.PaiementService;

import java.util.List;

/**
 * Implémentation du service pour la gestion des paiements.
 * 
 * Ce service gère les opérations CRUD de base sur les paiements.
 * Un paiement est lié à une commande et a un statut (EN_ATTENTE, VALIDE, REFUSE).
 * 
 * @Service : Composant Spring géré par le conteneur IoC.
 * @Transactional : Gestion des transactions automatique.
 */
@Service
@Transactional
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;

    /**
     * Injection par constructeur.
     */
    public PaiementServiceImpl(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    /**
     * Créer un nouveau paiement.
     */
    @Override
    public Paiement createPaiement(Paiement paiement) {
        if (paiement == null) {
            throw new BadRequestException("Le paiement ne peut pas être null");
        }
        if (paiement.getMontant() == null || paiement.getMontant() <= 0) {
            throw new BadRequestException("Le montant doit être positif");
        }
        return paiementRepository.save(paiement);
    }

    /**
     * Récupérer un paiement par son ID.
     */
    @Override
    @Transactional(readOnly = true)
    public Paiement getPaiementById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    /**
     * Récupérer tous les paiements.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    /**
     * Mettre à jour un paiement.
     */
    @Override
    public Paiement updatePaiement(Long id, Paiement paiement) {
        if (paiement == null) {
            throw new BadRequestException("Les données du paiement sont obligatoires");
        }
        
        Paiement paiementExistant = getPaiementById(id);
        
        if (paiement.getMontant() != null && paiement.getMontant() > 0) {
            paiementExistant.setMontant(paiement.getMontant());
        }
        if (paiement.getDatePaiement() != null) {
            paiementExistant.setDatePaiement(paiement.getDatePaiement());
        }
        if (paiement.getStatut() != null) {
            paiementExistant.setStatut(paiement.getStatut());
        }
        if (paiement.getMode() != null) {
            paiementExistant.setMode(paiement.getMode());
        }
        if (paiement.getCommande() != null) {
            paiementExistant.setCommande(paiement.getCommande());
        }
        
        return paiementRepository.save(paiementExistant);
    }

    /**
     * Supprimer un paiement.
     */
    @Override
    public void deletePaiement(Long id) {
        Paiement paiement = getPaiementById(id);
        paiementRepository.delete(paiement);
    }
}
