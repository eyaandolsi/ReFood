package tn.esprit.refood.events.controller;

import tn.esprit.refood.events.domain.Evenement;
import tn.esprit.refood.events.domain.Inscription;
import tn.esprit.refood.events.domain.StatutEvenement;
import tn.esprit.refood.events.domain.TypeEvenement;
import tn.esprit.refood.events.service.EvenementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EvenementController {
    private final EvenementService service;

    public EvenementController(EvenementService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evenement create(@RequestBody Evenement evenement) {
        return service.create(evenement);
    }

    @GetMapping
    public List<Evenement> findAll(
            @RequestParam(required = false) TypeEvenement type,
            @RequestParam(required = false) StatutEvenement statut) {
        return service.findAll(type, statut);
    }

    @GetMapping("/{id}")
    public Evenement findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Evenement update(@PathVariable Long id, @RequestBody Evenement evenement) {
        return service.update(id, evenement);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/publish")
    public Evenement publier(@PathVariable Long id) {
        return service.publier(id);
    }

    @PatchMapping("/{id}/cancel")
    public Evenement annuler(@PathVariable Long id) {
        return service.annuler(id);
    }

    @PatchMapping("/{id}/close")
    public Evenement cloturer(@PathVariable Long id) {
        return service.cloturer(id);
    }

    @PostMapping("/{id}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public Inscription inscrire(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String participantId = body.get("participantId");
        if (participantId == null || participantId.isBlank()) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "participantId est obligatoire");
        }
        return service.inscrire(id, participantId);
    }

    @DeleteMapping("/{id}/registrations/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void annulerInscription(@PathVariable Long id,
                                   @RequestParam String participantId) {
        service.annulerInscription(id, participantId);
    }
}
