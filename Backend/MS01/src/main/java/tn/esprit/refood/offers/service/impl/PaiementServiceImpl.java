package tn.esprit.refood.offers.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.refood.offers.entity.Paiement;
import tn.esprit.refood.offers.repository.PaiementRepository;
import tn.esprit.refood.offers.service.PaiementService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service pour la gestion des paiements.
 * 
 * Ce service gère les opérations CRUD de base sur les paiements.
 * Un paiement est lié à une commande et a un statut (EN_ATTENTE, VALIDE, REFUSE).
 * 
 * Note: Cette implémentation est basique. En production, on pourrait ajouter:
 * - Validation du montant (doit être positif)
 * - Logique métier (ex: vérifier que la commande existe)
 * - Gestion des transactions avec @Transactional
 * - Intégration avec un système de paiement externe
 * 
 * @Service : Composant Spring géré par le conteneur IoC.
 */
@Service
public class PaiementServiceImpl implements PaiementService {

    // TODO: Remplacer @Autowired par injection par constructeur
    @Autowired
    private PaiementRepository paiementRepository;

    /**
     * Créer un nouveau paiement.
     * 
     * Note: Ajouter des validations:
     * - Vérifier que la commande existe
     * - Vérifier que le montant est positif
     * - Initialiser le statut à EN_ATTENTE par défaut
     */
    @Override
    public Paiement createPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    /**
     * Récupérer un paiement par son ID.
     * 
     * Retourne null si le paiement n'existe pas.
     */
    @Override
    public Paiement getPaiementById(Long id) {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        return paiement.orElse(null);
    }

    /**
     * Récupérer tous les paiements.
     * 
     * Note: En production, ajouter pagination et filtres
     * (par commande, par statut, par date) pour optimiser les performances.
     */
    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    /**
     * Mettre à jour un paiement.
     * 
     * Permet de modifier le statut du paiement (ex: EN_ATTENTE -> VALIDE).
     */
    @Override
    public Paiement updatePaiement(Long id, Paiement paiement) {
        if (paiementRepository.existsById(id)) {
            paiement.setId(id);
            return paiementRepository.save(paiement);
        }
        return null;
    }

    /**
     * Supprimer un paiement.
     * 
     * Note: En production, vérifier si le paiement est validé
     * avant de permettre la suppression.
     */
    @Override
    public void deletePaiement(Long id) {
        paiementRepository.deleteById(id);
    }
}
