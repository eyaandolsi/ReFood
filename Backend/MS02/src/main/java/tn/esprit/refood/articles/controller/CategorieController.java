package tn.esprit.refood.articles.controller;

import tn.esprit.refood.articles.dto.CategorieRequest;
import tn.esprit.refood.articles.dto.CategorieResponse;
import tn.esprit.refood.articles.service.CategorieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Catégories")
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    public List<CategorieResponse> list() {
        return categorieService.findAll();
    }

    @GetMapping("/{id}")
    public CategorieResponse get(@PathVariable Long id) {
        return categorieService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CategorieResponse> create(@Valid @RequestBody CategorieRequest request) {
        CategorieResponse created = categorieService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public CategorieResponse update(@PathVariable Long id, @Valid @RequestBody CategorieRequest request) {
        return categorieService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
