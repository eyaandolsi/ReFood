package tn.esprit.refood.recipes.controller;

import tn.esprit.refood.recipes.model.Ingredient;
import tn.esprit.refood.recipes.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@CrossOrigin
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ingredient> lister(@RequestParam(required = false) String recherche) {
        return service.lister(recherche);
    }

    @GetMapping("/{id}")
    public Ingredient trouver(@PathVariable Long id) {
        return service.trouver(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient creer(@Valid @RequestBody Ingredient ingredient) {
        return service.creer(ingredient);
    }

    @PutMapping("/{id}")
    public Ingredient modifier(@PathVariable Long id, @Valid @RequestBody Ingredient ingredient) {
        return service.modifier(id, ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        service.supprimer(id);
    }
}