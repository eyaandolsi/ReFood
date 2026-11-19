package tn.esprit.refood.recipes.controller;

import tn.esprit.refood.recipes.model.Favori;
import tn.esprit.refood.recipes.service.FavoriService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoris")
@CrossOrigin
@Validated
public class FavoriController {

    private final FavoriService service;

    public FavoriController(FavoriService service) {
        this.service = service;
    }

    /** GET /api/favoris?utilisateurId=u1 */
    @GetMapping
    public List<Favori> lister(@RequestParam String utilisateurId) {
        return service.listerParUtilisateur(utilisateurId);
    }

    /** POST /api/favoris?utilisateurId=u1&recetteId=3&note=5 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Favori ajouter(@RequestParam String utilisateurId,
                          @RequestParam Long recetteId,
                          @RequestParam(required = false) @Min(1) @Max(5) Integer note) {
        return service.ajouter(utilisateurId, recetteId, note);
    }

    /** PATCH /api/favoris/{id}/note?note=4 */
    @PatchMapping("/{id}/note")
    public Favori noter(@PathVariable Long id, @RequestParam @Min(1) @Max(5) Integer note) {
        return service.noter(id, note);
    }

    /** DELETE /api/favoris?utilisateurId=u1&recetteId=3 */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retirer(@RequestParam String utilisateurId, @RequestParam Long recetteId) {
        service.retirer(utilisateurId, recetteId);
    }
}