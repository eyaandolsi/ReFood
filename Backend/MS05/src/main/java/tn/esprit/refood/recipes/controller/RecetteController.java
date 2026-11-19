package tn.esprit.refood.recipes.controller;

import tn.esprit.refood.recipes.model.Difficulte;
import tn.esprit.refood.recipes.model.Recette;
import tn.esprit.refood.recipes.service.RecetteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recettes")
@CrossOrigin
public class RecetteController {

    private final RecetteService service;

    public RecetteController(RecetteService service) {
        this.service = service;
    }

    /** GET /api/recettes?titre=...&difficulte=FACILE&tempsMax=30 */
    @GetMapping
    public List<Recette> lister(@RequestParam(required = false) String titre,
                                @RequestParam(required = false) Difficulte difficulte,
                                @RequestParam(required = false) Integer tempsMax) {
        return service.lister(titre, difficulte, tempsMax);
    }

    @GetMapping("/{id}")
    public Recette trouver(@PathVariable Long id) {
        return service.trouver(id);
    }

    /**
     * GET /api/recettes/recommandations?utilisateurId=u1&antiGaspi=true
     * Recettes triees par score de correspondance avec le garde-manger.
     * antiGaspi=true : ne considere que les produits proches de la peremption.
     */
    @GetMapping("/recommandations")
    public List<RecetteService.RecetteScore> recommander(@RequestParam String utilisateurId,
                                                         @RequestParam(defaultValue = "false") boolean antiGaspi) {
        return service.recommander(utilisateurId, antiGaspi);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recette creer(@Valid @RequestBody Recette recette) {
        return service.creer(recette);
    }

    @PutMapping("/{id}")
    public Recette modifier(@PathVariable Long id, @Valid @RequestBody Recette recette) {
        return service.modifier(id, recette);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        service.supprimer(id);
    }
}