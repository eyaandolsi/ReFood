package tn.esprit.refood.donations.controller;

import tn.esprit.refood.donations.dto.*;
import tn.esprit.refood.donations.service.BesoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/needs")
@RequiredArgsConstructor
@Tag(name = "Besoins")
public class BesoinController {

    private final BesoinService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Déclarer un besoin")
    public BesoinResponse create(@Valid @RequestBody BesoinRequest req) {
        return service.create(req);
    }

    @GetMapping
    @Operation(summary = "Lister les besoins (filtre optionnel par association)")
    public List<BesoinResponse> findAll(@RequestParam(required = false) Long associationId) {
        return service.findAll(associationId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un besoin")
    public BesoinResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un besoin")
    public BesoinResponse update(@PathVariable Long id, @Valid @RequestBody BesoinRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un besoin")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
