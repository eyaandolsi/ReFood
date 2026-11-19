package tn.esprit.refood.offers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.refood.offers.dto.CategorieRequestDTO;
import tn.esprit.refood.offers.dto.CategorieResponseDTO;
import tn.esprit.refood.offers.entity.Categorie;
import tn.esprit.refood.offers.mapper.CategorieMapper;
import tn.esprit.refood.offers.service.CategorieService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des catégories.
 * 
 * Ce contrôleur gère les catégories d'offres.
 * Une catégorie peut avoir plusieurs offres (relation OneToMany).
 * Le nom de la catégorie est unique (contrainte unique en base de données).
 * 
 * @RestController : Convertit automatiquement les retours en JSON.
 * @RequestMapping : Base path /api/categories pour tous les endpoints.
 * @Tag : Documentation Swagger pour les catégories.
 */
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Gestion des catégories")
public class CategorieController {

    private final CategorieService categorieService;
    private final CategorieMapper categorieMapper;

    /**
     * Constructeur pour l'injection de dépendances.
     */
    public CategorieController(CategorieService categorieService, CategorieMapper categorieMapper) {
        this.categorieService = categorieService;
        this.categorieMapper = categorieMapper;
    }

    /**
     * Créer une nouvelle catégorie.
     */
    @PostMapping
    @Operation(summary = "Créer une catégorie")
    public ResponseEntity<CategorieResponseDTO> createCategorie(@Valid @RequestBody CategorieRequestDTO categorieRequestDTO) {
        Categorie categorie = categorieMapper.toEntity(categorieRequestDTO);
        Categorie createdCategorie = categorieService.createCategorie(categorie);
        CategorieResponseDTO responseDTO = categorieMapper.toResponseDTO(createdCategorie);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Récupérer une catégorie par son ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une catégorie par ID")
    public ResponseEntity<CategorieResponseDTO> getCategorieById(@PathVariable Long id) {
        Categorie categorie = categorieService.getCategorieById(id);
        CategorieResponseDTO responseDTO = categorieMapper.toResponseDTO(categorie);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Récupérer toutes les catégories.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les catégories")
    public ResponseEntity<List<CategorieResponseDTO>> getAllCategories() {
        List<Categorie> categories = categorieService.getAllCategories();
        List<CategorieResponseDTO> responseDTOs = categories.stream()
                .map(categorieMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Mettre à jour une catégorie.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une catégorie")
    public ResponseEntity<CategorieResponseDTO> updateCategorie(@PathVariable Long id, @Valid @RequestBody CategorieRequestDTO categorieRequestDTO) {
        Categorie categorie = categorieMapper.toEntity(categorieRequestDTO);
        Categorie updatedCategorie = categorieService.updateCategorie(id, categorie);
        CategorieResponseDTO responseDTO = categorieMapper.toResponseDTO(updatedCategorie);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Supprimer une catégorie.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une catégorie")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}
