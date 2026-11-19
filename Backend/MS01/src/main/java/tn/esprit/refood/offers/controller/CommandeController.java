package tn.esprit.refood.offers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.refood.offers.dto.CommandeRequestDTO;
import tn.esprit.refood.offers.dto.CommandeResponseDTO;
import tn.esprit.refood.offers.entity.Commande;
import tn.esprit.refood.offers.mapper.CommandeMapper;
import tn.esprit.refood.offers.service.CommandeService;

import java.util.List;
import java.util.stream.Collectors;

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

    private final CommandeService commandeService;
    private final CommandeMapper commandeMapper;

    /**
     * Constructeur pour l'injection de dépendances.
     */
    public CommandeController(CommandeService commandeService, CommandeMapper commandeMapper) {
        this.commandeService = commandeService;
        this.commandeMapper = commandeMapper;
    }

    /**
     * Créer une nouvelle commande.
     */
    @PostMapping
    @Operation(summary = "Créer une commande")
    public ResponseEntity<CommandeResponseDTO> createCommande(@Valid @RequestBody CommandeRequestDTO commandeRequestDTO) {
        Commande commande = commandeMapper.toEntity(commandeRequestDTO);
        Commande createdCommande = commandeService.createCommande(commande);
        CommandeResponseDTO responseDTO = commandeMapper.toResponseDTO(createdCommande);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Récupérer une commande par son ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une commande par ID")
    public ResponseEntity<CommandeResponseDTO> getCommandeById(@PathVariable Long id) {
        Commande commande = commandeService.getCommandeById(id);
        CommandeResponseDTO responseDTO = commandeMapper.toResponseDTO(commande);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Récupérer toutes les commandes.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les commandes")
    public ResponseEntity<List<CommandeResponseDTO>> getAllCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();
        List<CommandeResponseDTO> responseDTOs = commandes.stream()
                .map(commandeMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Mettre à jour une commande.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une commande")
    public ResponseEntity<CommandeResponseDTO> updateCommande(@PathVariable Long id, @Valid @RequestBody CommandeRequestDTO commandeRequestDTO) {
        Commande commande = commandeMapper.toEntity(commandeRequestDTO);
        Commande updatedCommande = commandeService.updateCommande(id, commande);
        CommandeResponseDTO responseDTO = commandeMapper.toResponseDTO(updatedCommande);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Supprimer une commande.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une commande")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }
}
