
package tn.esprit.refood.offers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.refood.offers.entity.Offre;
import tn.esprit.refood.offers.service.OffreService;

import java.util.List;

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

    // Injection par constructeur (recommandée par Spring pour l'immutabilité et les tests)
    // Le service est déclaré 'final' pour garantir qu'il ne peut pas être modifié après l'injection
    private final OffreService offreService;

    /**
     * Constructeur pour l'injection de dépendances.
     * 
     * L'injection par constructeur est préférée à @Autowired sur les champs car:
     * - Elle permet de rendre les champs final (immutabilité)
     * - Elle facilite les tests unitaires (on peut facilement mocker les dépendances)
     * - Elle garantit que toutes les dépendances sont présentes à la création de l'objet
     */
    public OffreController(OffreService offreService) {
        this.offreService = offreService;
    }

    /**
     * Créer une nouvelle offre.
     * 
     * @PostMapping : Mappe les requêtes HTTP POST sur cet endpoint.
     * @Operation : Documentation Swagger décrivant l'opération.
     * @RequestBody : Le corps de la requête HTTP est automatiquement désérialisé en objet Offre.
     * 
     * Retourne HTTP 201 (CREATED) pour indiquer que la ressource a été créée avec succès.
     * C'est une bonne pratique RESTful de retourner 201 plutôt que 200 pour une création.
     */
    @PostMapping
    @Operation(summary = "Créer une offre")
    public ResponseEntity<Offre> createOffre(
            @RequestBody Offre offre) {

        // Délègue la logique métier au service layer (séparation des responsabilités)
        Offre createdOffre = offreService.createOffre(offre);

        // Retourne 201 CREATED avec l'offre créée dans le corps de la réponse
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdOffre);
    }

    /**
     * Récupérer une offre par son identifiant.
     * 
     * @GetMapping : Mappe les requêtes HTTP GET sur cet endpoint.
     * @PathVariable : Extrait l'ID de l'URL (ex: /api/offres/123 -> id = 123).
     * 
     * Retourne 404 NOT FOUND si l'offre n'existe pas, ce qui est le comportement REST standard.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une offre par ID")
    public ResponseEntity<Offre> getOffreById(
            @PathVariable Long id) {

        Offre offre = offreService.getOffreById(id);

        // Gestion du cas où l'offre n'existe pas
        if (offre == null) {
            return ResponseEntity.notFound().build();
        }

        // Retourne 200 OK avec l'offre trouvée
        return ResponseEntity.ok(offre);
    }

    /**
     * Récupérer toutes les offres disponibles.
     * 
     * Note : Pour une application avec beaucoup de données, il faudrait ajouter
     * de la pagination (ex: /api/offres?page=0&size=10) pour éviter les
     * problèmes de performance.
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les offres")
    public ResponseEntity<List<Offre>> getAllOffres() {

        List<Offre> offres = offreService.getAllOffres();

        return ResponseEntity.ok(offres);
    }

    /**
     * Mettre à jour une offre existante.
     * 
     * @PutMapping : Mappe les requêtes HTTP PUT pour les mises à jour complètes.
     *              Pour une mise à jour partielle (PATCH), on utiliserait @PatchMapping.
     * 
     * L'ID dans l'URL doit correspondre à l'offre à mettre à jour.
     * Le corps de la requête contient les nouvelles données.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une offre")
    public ResponseEntity<Offre> updateOffre(
            @PathVariable Long id,
            @RequestBody Offre offre) {

        Offre updatedOffre = offreService.updateOffre(id, offre);

        if (updatedOffre == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedOffre);
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
