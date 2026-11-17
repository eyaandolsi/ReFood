package tn.esprit.refood.offers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.refood.offers.entity.Paiement;
import tn.esprit.refood.offers.service.PaiementService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des paiements.
 * 
 * Ce contrôleur gère les paiements associés aux commandes.
 * Un paiement est lié à une commande et a un statut (EN_ATTENTE, VALIDE, REFUSE)
 * et un mode de paiement (CARTE_BANCAIRE, CHEQUE, ESPECES).
 * 
 * @RestController : Convertit automatiquement les retours en JSON.
 * @RequestMapping : Base path /api/paiements pour tous les endpoints.
 * @Tag : Documentation Swagger pour les paiements.
 */
@RestController
@RequestMapping("/api/paiements")
@Tag(name = "Paiements", description = "Gestion des paiements")
public class PaiementController {

    // TODO: Remplacer @Autowired par injection par constructeur
    @Autowired
    private PaiementService paiementService;

    /**
     * Créer un nouveau paiement.
     * 
     * Le paiement doit être lié à une commande existante.
     * Le statut initial est EN_ATTENTE par défaut.
     */
    @PostMapping
    @Operation(summary = "Créer un paiement")
    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) {
        Paiement createdPaiement = paiementService.createPaiement(paiement);
        // TODO: Retourner 201 CREATED au lieu de 200 OK
        return ResponseEntity.ok(createdPaiement);
    }

    /**
     * Récupérer un paiement par son ID.
     * 
     * Retourne 404 si le paiement n'existe pas.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un paiement par ID")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Long id) {
        Paiement paiement = paiementService.getPaiementById(id);
        if (paiement != null) {
            return ResponseEntity.ok(paiement);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Récupérer tous les paiements.
     * 
     * Note: En production, ajouter des filtres (par commande, par statut, par date)
     * et la pagination pour optimiser les performances.
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les paiements")
    public ResponseEntity<List<Paiement>> getAllPaiements() {
        List<Paiement> paiements = paiementService.getAllPaiements();
        return ResponseEntity.ok(paiements);
    }

    /**
     * Mettre à jour un paiement.
     * 
     * Permet de modifier le statut du paiement (ex: EN_ATTENTE -> VALIDE).
     * Une fois validé, le paiement ne devrait plus être modifié.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un paiement")
    public ResponseEntity<Paiement> updatePaiement(@PathVariable Long id, @RequestBody Paiement paiement) {
        Paiement updatedPaiement = paiementService.updatePaiement(id, paiement);
        if (updatedPaiement != null) {
            return ResponseEntity.ok(updatedPaiement);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Supprimer un paiement.
     * 
     * Attention: La suppression d'un paiement peut affecter la commande associée.
     * En production, préférer une suppression logique ou bloquer la suppression
     * si le paiement est validé.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un paiement")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}
