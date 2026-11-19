package tn.esprit.refood.donations.controller;

import tn.esprit.refood.donations.dto.*;
import tn.esprit.refood.donations.service.AssociationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/associations")
@RequiredArgsConstructor
@Tag(name = "Associations")
public class AssociationController {

    private final AssociationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une association")
    public AssociationResponse create(@Valid @RequestBody AssociationRequest req) {
        return service.create(req);
    }

    @GetMapping
    @Operation(summary = "Lister les associations")
    public List<AssociationResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une association")
    public AssociationResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une association")
    public AssociationResponse update(@PathVariable Long id, @Valid @RequestBody AssociationRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}/verify")
    @Operation(summary = "Vérifier une association")
    public AssociationResponse verifier(@PathVariable Long id) {
        return service.verifier(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une association")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
