package tn.esprit.refood.offers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.refood.offers.entity.Categorie;
import tn.esprit.refood.offers.service.CategorieService;

import java.util.List;

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

    // TODO: Remplacer @Autowired par injection par constructeur
    @Autowired
    private CategorieService categorieService;

    /**
     * Créer une nouvelle catégorie.
     * 
     * Le nom de la catégorie doit être unique.
     * Une catégorie peut être créée sans offres initialement.
     */
    @PostMapping
    @Operation(summary = "Créer une catégorie")
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorie) {
        Categorie createdCategorie = categorieService.createCategorie(categorie);
        // TODO: Retourner 201 CREATED au lieu de 200 OK
        return ResponseEntity.ok(createdCategorie);
    }

    /**
     * Récupérer une catégorie par son ID.
     * 
     * Retourne 404 si la catégorie n'existe pas.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une catégorie par ID")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
        Categorie categorie = categorieService.getCategorieById(id);
        if (categorie != null) {
            return ResponseEntity.ok(categorie);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Récupérer toutes les catégories.
     * 
     * Note: Les offres associées ne sont pas chargées par défaut (lazy loading)
     * pour éviter les problèmes de performance N+1.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les catégories")
    public ResponseEntity<List<Categorie>> getAllCategories() {
        List<Categorie> categories = categorieService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Mettre à jour une catégorie.
     * 
     * Attention: Le nom de la catégorie reste unique.
     * Si on modifie le nom, il faut vérifier qu'il n'existe pas déjà.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une catégorie")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorie) {
        Categorie updatedCategorie = categorieService.updateCategorie(id, categorie);
        if (updatedCategorie != null) {
            return ResponseEntity.ok(updatedCategorie);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Supprimer une catégorie.
     * 
     * Attention: La suppression supprimera aussi toutes les offres associées
     * (cascade=ALL dans l'entité). En production, vérifier d'abord
     * qu'il n'y a pas d'offres liées ou utiliser une suppression logique.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une catégorie")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}
