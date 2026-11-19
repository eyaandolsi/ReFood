package tn.esprit.refood.articles.controller;

import tn.esprit.refood.articles.dto.CommentaireRequest;
import tn.esprit.refood.articles.dto.CommentaireResponse;
import tn.esprit.refood.articles.service.CommentaireService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;

    @PutMapping("/{id}")
    public CommentaireResponse update(@PathVariable Long id, @Valid @RequestBody CommentaireRequest request) {
        return commentaireService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String auteurId) {
        commentaireService.delete(id, auteurId);
        return ResponseEntity.noContent().build();
    }
}
