package tn.esprit.refood.offers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.refood.offers.dto.PaiementRequestDTO;
import tn.esprit.refood.offers.dto.PaiementResponseDTO;
import tn.esprit.refood.offers.entity.Paiement;
import tn.esprit.refood.offers.mapper.PaiementMapper;
import tn.esprit.refood.offers.service.PaiementService;

import java.util.List;
import java.util.stream.Collectors;

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

    private final PaiementService paiementService;
    private final PaiementMapper paiementMapper;

    /**
     * Constructeur pour l'injection de dépendances.
     */
    public PaiementController(PaiementService paiementService, PaiementMapper paiementMapper) {
        this.paiementService = paiementService;
        this.paiementMapper = paiementMapper;
    }

    /**
     * Créer un nouveau paiement.
     */
    @PostMapping
    @Operation(summary = "Créer un paiement")
    public ResponseEntity<PaiementResponseDTO> createPaiement(@Valid @RequestBody PaiementRequestDTO paiementRequestDTO) {
        Paiement paiement = paiementMapper.toEntity(paiementRequestDTO);
        Paiement createdPaiement = paiementService.createPaiement(paiement);
        PaiementResponseDTO responseDTO = paiementMapper.toResponseDTO(createdPaiement);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Récupérer un paiement par son ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un paiement par ID")
    public ResponseEntity<PaiementResponseDTO> getPaiementById(@PathVariable Long id) {
        Paiement paiement = paiementService.getPaiementById(id);
        PaiementResponseDTO responseDTO = paiementMapper.toResponseDTO(paiement);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Récupérer tous les paiements.
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les paiements")
    public ResponseEntity<List<PaiementResponseDTO>> getAllPaiements() {
        List<Paiement> paiements = paiementService.getAllPaiements();
        List<PaiementResponseDTO> responseDTOs = paiements.stream()
                .map(paiementMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Mettre à jour un paiement.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un paiement")
    public ResponseEntity<PaiementResponseDTO> updatePaiement(@PathVariable Long id, @Valid @RequestBody PaiementRequestDTO paiementRequestDTO) {
        Paiement paiement = paiementMapper.toEntity(paiementRequestDTO);
        Paiement updatedPaiement = paiementService.updatePaiement(id, paiement);
        PaiementResponseDTO responseDTO = paiementMapper.toResponseDTO(updatedPaiement);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Supprimer un paiement.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un paiement")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}
