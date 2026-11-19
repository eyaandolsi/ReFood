package tn.esprit.refood.recipes.controller;

import tn.esprit.refood.recipes.model.ProduitGardeManger;
import tn.esprit.refood.recipes.service.ProduitGardeMangerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garde-manger")
@CrossOrigin
public class ProduitGardeMangerController {

    private final ProduitGardeMangerService service;

    public ProduitGardeMangerController(ProduitGardeMangerService service) {
        this.service = service;
    }

    /** GET /api/garde-manger?utilisateurId=u1 */
    @GetMapping
    public List<ProduitGardeManger> lister(@RequestParam String utilisateurId) {
        return service.listerParUtilisateur(utilisateurId);
    }

    /** GET /api/garde-manger/proches-peremption?utilisateurId=u1 */
    @GetMapping("/proches-peremption")
    public List<ProduitGardeManger> prochesPeremption(@RequestParam String utilisateurId) {
        return service.listerProchesPeremption(utilisateurId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProduitGardeManger ajouter(@Valid @RequestBody ProduitGardeManger produit) {
        return service.ajouter(produit);
    }

    @PutMapping("/{id}")
    public ProduitGardeManger modifier(@PathVariable Long id, @Valid @RequestBody ProduitGardeManger produit) {
        return service.modifier(id, produit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        service.supprimer(id);
    }
}