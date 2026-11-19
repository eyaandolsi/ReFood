package tn.esprit.refood.articles.controller;

import tn.esprit.refood.articles.dto.ArticleRequest;
import tn.esprit.refood.articles.dto.ArticleResponse;
import tn.esprit.refood.articles.dto.CommentaireRequest;
import tn.esprit.refood.articles.dto.CommentaireResponse;
import tn.esprit.refood.articles.dto.LikeRequest;
import tn.esprit.refood.articles.dto.LikeResponse;
import tn.esprit.refood.articles.entity.StatutArticle;
import tn.esprit.refood.articles.service.ArticleService;
import tn.esprit.refood.articles.service.CommentaireService;
import tn.esprit.refood.articles.service.LikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@Tag(name = "Articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentaireService commentaireService;
    private final LikeService likeService;

    // ----- Articles -----

    @PostMapping
    public ResponseEntity<ArticleResponse> create(@Valid @RequestBody ArticleRequest request) {
        ArticleResponse created = articleService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    /** Filtres optionnels : ?statut=PUBLIE&categorieId=1 */
    @GetMapping
    public List<ArticleResponse> list(@RequestParam(required = false) StatutArticle statut,
                                      @RequestParam(required = false) Long categorieId) {
        return articleService.search(statut, categorieId);
    }

    @GetMapping("/{id}")
    public ArticleResponse get(@PathVariable Long id) {
        return articleService.findById(id);
    }

    @PutMapping("/{id}")
    public ArticleResponse update(@PathVariable Long id, @Valid @RequestBody ArticleRequest request) {
        return articleService.update(id, request);
    }

    @PatchMapping("/{id}/publish")
    public ArticleResponse publish(@PathVariable Long id) {
        return articleService.publish(id);
    }

    @PatchMapping("/{id}/archive")
    public ArticleResponse archive(@PathVariable Long id) {
        return articleService.archive(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ----- Commentaires d'un article -----

    @GetMapping("/{id}/comments")
    public List<CommentaireResponse> comments(@PathVariable Long id) {
        return commentaireService.findByArticle(id);
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentaireResponse addComment(@PathVariable Long id, @Valid @RequestBody CommentaireRequest request) {
        return commentaireService.add(id, request);
    }

    // ----- Likes -----

    @PostMapping("/{id}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeResponse like(@PathVariable Long id, @Valid @RequestBody LikeRequest request) {
        return likeService.like(id, request.utilisateurId());
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<Void> unlike(@PathVariable Long id, @RequestParam String utilisateurId) {
        likeService.unlike(id, utilisateurId);
        return ResponseEntity.noContent().build();
    }
}
