
package tn.esprit.refood.offers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.refood.offers.dto.OffreRequestDTO;
import tn.esprit.refood.offers.dto.OffreResponseDTO;
import tn.esprit.refood.offers.entity.Offre;
import tn.esprit.refood.offers.mapper.OffreMapper;
import tn.esprit.refood.offers.service.OffreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des offres.
 * 
 * Ce contrôleur expose les endpoints HTTP pour manipuler les offres de l'application REFOOD.
 * Il suit le pattern REST et utilise Swagger pour la documentation de l'API.
 * 
 * @RestController : Indique que cette classe est un contrôleur Spring MVC où chaque méthode
 *                  retourne un objet domaine au lieu d'une vue. Les réponses sont automatiquement
 *                  converties en JSON.
 * @RequestMapping : Définit le chemin de base pour tous les endpoints de ce contrôleur (/api/offres).
 * @Tag : Annotation Swagger pour regrouper les endpoints dans la documentation UI.
 */
@RestController
@RequestMapping("/api/offres")
@Tag(name = "Offres", description = "Gestion des offres REFOOD")
public class OffreController {

    private final OffreService offreService;
    private final OffreMapper offreMapper;

    /**
     * Constructeur pour l'injection de dépendances.
     */
    public OffreController(OffreService offreService, OffreMapper offreMapper) {
        this.offreService = offreService;
        this.offreMapper = offreMapper;
    }

    /**
     * Créer une nouvelle offre.
     */
    @PostMapping
    @Operation(summary = "Créer une offre")
    public ResponseEntity<OffreResponseDTO> createOffre(
            @Valid @RequestBody OffreRequestDTO offreRequestDTO) {

        Offre offre = offreMapper.toEntity(offreRequestDTO);
        Offre createdOffre = offreService.createOffre(offre);
        OffreResponseDTO responseDTO = offreMapper.toResponseDTO(createdOffre);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    /**
     * Récupérer une offre par son identifiant.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une offre par ID")
    public ResponseEntity<OffreResponseDTO> getOffreById(
            @PathVariable Long id) {

        Offre offre = offreService.getOffreById(id);
        OffreResponseDTO responseDTO = offreMapper.toResponseDTO(offre);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Récupérer toutes les offres disponibles.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les offres")
    public ResponseEntity<List<OffreResponseDTO>> getAllOffres() {

        List<Offre> offres = offreService.getAllOffres();
        List<OffreResponseDTO> responseDTOs = offres.stream()
                .map(offreMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Mettre à jour une offre existante.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une offre")
    public ResponseEntity<OffreResponseDTO> updateOffre(
            @PathVariable Long id,
            @Valid @RequestBody OffreRequestDTO offreRequestDTO) {

        Offre offre = offreMapper.toEntity(offreRequestDTO);
        Offre updatedOffre = offreService.updateOffre(id, offre);
        OffreResponseDTO responseDTO = offreMapper.toResponseDTO(updatedOffre);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Supprimer une offre.
     * 
     * @DeleteMapping : Mappe les requêtes HTTP DELETE pour la suppression.
     * 
     * Retourne 204 NO CONTENT (sans corps) car la ressource n'existe plus.
     * C'est la réponse standard REST pour une suppression réussie.
     * 
     * Note : En production, on préfère souvent une suppression logique (soft delete)
     * en ajoutant un champ 'deleted' ou 'actif' plutôt qu'une suppression physique.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une offre")
    public ResponseEntity<Void> deleteOffre(
            @PathVariable Long id) {

        offreService.deleteOffre(id);

        return ResponseEntity.noContent().build();
    }
}
