package tn.esprit.refood.offers.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.refood.offers.entity.Commande;
import tn.esprit.refood.offers.exception.BadRequestException;
import tn.esprit.refood.offers.exception.ResourceNotFoundException;
import tn.esprit.refood.offers.repository.CommandeRepository;
import tn.esprit.refood.offers.service.CommandeService;

import java.util.List;

/**
 * Implémentation du service pour la gestion des commandes.
 * 
 * Ce service gère les opérations CRUD de base sur les commandes.
 * Une commande est liée à une offre et peut avoir plusieurs paiements.
 * 
 * @Service : Composant Spring géré par le conteneur IoC.
 * @Transactional : Gestion des transactions automatique.
 */
@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;

    /**
     * Injection par constructeur.
     */
    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    /**
     * Créer une nouvelle commande.
     */
    @Override
    public Commande createCommande(Commande commande) {
        if (commande == null) {
            throw new BadRequestException("La commande ne peut pas être null");
        }
        if (commande.getMontantTotal() == null || commande.getMontantTotal() <= 0) {
            throw new BadRequestException("Le montant total doit être positif");
        }
        return commandeRepository.save(commande);
    }

    /**
     * Récupérer une commande par son ID.
     */
    @Override
    @Transactional(readOnly = true)
    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", id));
    }

    /**
     * Récupérer toutes les commandes.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    /**
     * Mettre à jour une commande.
     */
    @Override
    public Commande updateCommande(Long id, Commande commande) {
        if (commande == null) {
            throw new BadRequestException("Les données de la commande sont obligatoires");
        }
        
        Commande commandeExistante = getCommandeById(id);
        
        if (commande.getDateCommande() != null) {
            commandeExistante.setDateCommande(commande.getDateCommande());
        }
        if (commande.getMontantTotal() != null && commande.getMontantTotal() > 0) {
            commandeExistante.setMontantTotal(commande.getMontantTotal());
        }
        if (commande.getStatut() != null) {
            commandeExistante.setStatut(commande.getStatut());
        }
        if (commande.getOffre() != null) {
            commandeExistante.setOffre(commande.getOffre());
        }
        
        return commandeRepository.save(commandeExistante);
    }

    /**
     * Supprimer une commande.
     */
    @Override
    public void deleteCommande(Long id) {
        Commande commande = getCommandeById(id);
        commandeRepository.delete(commande);
    }
}
