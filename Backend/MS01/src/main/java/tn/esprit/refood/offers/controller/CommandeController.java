package tn.esprit.refood.offers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.refood.offers.entity.Commande;
import tn.esprit.refood.offers.service.CommandeService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des commandes.
 * 
 * Ce contrôleur gère les opérations CRUD sur les commandes.
 * Une commande est liée à une offre et peut avoir plusieurs paiements.
 * 
 * @RestController : Convertit automatiquement les retours en JSON.
 * @RequestMapping : Base path /api/commandes pour tous les endpoints.
 * @Tag : Documentation Swagger pour les commandes.
 */
@RestController
@RequestMapping("/api/commandes")
@Tag(name = "Commandes", description = "Gestion des commandes")
public class CommandeController {

    // TODO: Remplacer @Autowired par injection par constructeur (comme OffreController)
    // pour une meilleure testabilité et immutabilité
    @Autowired
    private CommandeService commandeService;

    /**
     * Créer une nouvelle commande.
     * 
     * La commande doit être liée à une offre existante via l'ID de l'offre.
     * Le statut initial de la commande est EN_ATTENTE par défaut.
     */
    @PostMapping
    @Operation(summary = "Créer une commande")
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        Commande createdCommande = commandeService.createCommande(commande);
        // TODO: Retourner 201 CREATED au lieu de 200 OK pour respecter REST
        return ResponseEntity.ok(createdCommande);
    }

    /**
     * Récupérer une commande par son ID.
     * 
     * Retourne 404 si la commande n'existe pas.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une commande par ID")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Commande commande = commandeService.getCommandeById(id);
        if (commande != null) {
            return ResponseEntity.ok(commande);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Récupérer toutes les commandes.
     * 
     * Note: En production, ajouter la pagination et des filtres
     * (par statut, par date, par offre) pour optimiser les performances.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les commandes")
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }

    /**
     * Mettre à jour une commande.
     * 
     * Permet de modifier le statut de la commande (ex: EN_ATTENTE -> CONFIRMEE).
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une commande")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        Commande updatedCommande = commandeService.updateCommande(id, commande);
        if (updatedCommande != null) {
            return ResponseEntity.ok(updatedCommande);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Supprimer une commande.
     * 
     * Attention: La suppression supprimera aussi les paiements associés
     * (cascade=ALL dans l'entité). En production, préférer une suppression logique.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une commande")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }
}
