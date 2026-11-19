package tn.esprit.refood.donations.controller;

import tn.esprit.refood.donations.dto.*;
import tn.esprit.refood.donations.entity.StatutDon;
import tn.esprit.refood.donations.service.DonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
@Tag(name = "Dons")
public class DonController {

    private final DonService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Demander un don")
    public DonResponse create(@Valid @RequestBody DonRequest req) {
        return service.create(req);
    }

    @GetMapping
    @Operation(summary = "Lister les dons (filtres : associationId ou statut)")
    public List<DonResponse> findAll(@RequestParam(required = false) Long associationId,
                                     @RequestParam(required = false) StatutDon statut) {
        return service.findAll(associationId, statut);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un don")
    public DonResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Historique des changements de statut")
    public List<HistoriqueResponse> historique(@PathVariable Long id) {
        return service.historique(id);
    }

    @PatchMapping("/{id}/accept")
    @Operation(summary = "Accepter un don")
    public DonResponse accepter(@PathVariable Long id) {
        return service.accepter(id);
    }

    @PatchMapping("/{id}/refuse")
    @Operation(summary = "Refuser un don")
    public DonResponse refuser(@PathVariable Long id, @Valid @RequestBody RefusRequest req) {
        return service.refuser(id, req.motif());
    }

    @PatchMapping("/{id}/receive")
    @Operation(summary = "Marquer un don comme reçu")
    public DonResponse marquerRecu(@PathVariable Long id) {
        return service.marquerRecu(id);
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Annuler un don")
    public DonResponse annuler(@PathVariable Long id) {
        return service.annuler(id);
    }

    @PostMapping("/{id}/slot")
    @Operation(summary = "Planifier un créneau de retrait")
    public DonResponse planifier(@PathVariable Long id, @Valid @RequestBody CreneauRequest req) {
        return service.planifier(id, req);
    }

    @PatchMapping("/{id}/slot/confirm")
    @Operation(summary = "Confirmer le créneau de retrait")
    public DonResponse confirmerCreneau(@PathVariable Long id) {
        return service.confirmerCreneau(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un don")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
