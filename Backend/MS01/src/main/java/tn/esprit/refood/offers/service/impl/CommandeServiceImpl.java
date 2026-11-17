package tn.esprit.refood.offers.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.refood.offers.entity.Commande;
import tn.esprit.refood.offers.repository.CommandeRepository;
import tn.esprit.refood.offers.service.CommandeService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service pour la gestion des commandes.
 * 
 * Ce service gère les opérations CRUD de base sur les commandes.
 * Une commande est liée à une offre et peut avoir plusieurs paiements.
 * 
 * Note: Cette implémentation est basique avec des opérations CRUD simples.
 * En production, on pourrait ajouter:
 * - Validation des données (montant positif, offre existante)
 * - Logique métier (ex: vérifier le stock avant de créer une commande)
 * - Gestion des transactions avec @Transactional
 * 
 * @Service : Composant Spring géré par le conteneur IoC.
 */
@Service
public class CommandeServiceImpl implements CommandeService {

    // TODO: Remplacer @Autowired par injection par constructeur
    @Autowired
    private CommandeRepository commandeRepository;

    /**
     * Créer une nouvelle commande.
     * 
     * Note: Ajouter des validations:
     * - Vérifier que l'offre existe et est disponible
     * - Vérifier que le montant est positif
     * - Initialiser le statut à EN_ATTENTE par défaut
     */
    @Override
    public Commande createCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    /**
     * Récupérer une commande par son ID.
     * 
     * Retourne null si la commande n'existe pas.
     * En production, on pourrait lancer une exception à la place.
     */
    @Override
    public Commande getCommandeById(Long id) {
        Optional<Commande> commande = commandeRepository.findById(id);
        return commande.orElse(null);
    }

    /**
     * Récupérer toutes les commandes.
     * 
     * Note: En production, ajouter pagination et filtres
     * (par statut, par date, par offre) pour optimiser les performances.
     */
    @Override
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    /**
     * Mettre à jour une commande.
     * 
     * Cette méthode vérifie d'abord si la commande existe,
     * puis met à jour les données.
     * 
     * Note: Comme OffreServiceImpl, on pourrait utiliser le pattern
     * "update by merge" pour plus de contrôle.
     */
    @Override
    public Commande updateCommande(Long id, Commande commande) {
        if (commandeRepository.existsById(id)) {
            commande.setId(id);
            return commandeRepository.save(commande);
        }
        return null;
    }

    /**
     * Supprimer une commande.
     * 
     * Attention: La suppression supprimera aussi les paiements associés
     * (cascade=ALL dans l'entité).
     */
    @Override
    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }
}
