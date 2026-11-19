package tn.esprit.refood.articles.service;

import tn.esprit.refood.articles.dto.CategorieRequest;
import tn.esprit.refood.articles.dto.CategorieResponse;
import tn.esprit.refood.articles.entity.Categorie;
import tn.esprit.refood.articles.exception.ConflictException;
import tn.esprit.refood.articles.exception.ResourceNotFoundException;
import tn.esprit.refood.articles.repository.ArticleRepository;
import tn.esprit.refood.articles.repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<CategorieResponse> findAll() {
        return categorieRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CategorieResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public CategorieResponse create(CategorieRequest request) {
        if (categorieRepository.existsByNomIgnoreCase(request.nom())) {
            throw new ConflictException("Une catégorie nommée '" + request.nom() + "' existe déjà");
        }
        Categorie c = new Categorie();
        c.setNom(request.nom());
        c.setDescription(request.description());
        return toResponse(categorieRepository.save(c));
    }

    public CategorieResponse update(Long id, CategorieRequest request) {
        Categorie c = getOrThrow(id);
        if (!c.getNom().equalsIgnoreCase(request.nom())
                && categorieRepository.existsByNomIgnoreCase(request.nom())) {
            throw new ConflictException("Une catégorie nommée '" + request.nom() + "' existe déjà");
        }
        c.setNom(request.nom());
        c.setDescription(request.description());
        return toResponse(categorieRepository.save(c));
    }

    public void delete(Long id) {
        Categorie c = getOrThrow(id);
        if (articleRepository.existsByCategorieId(id)) {
            throw new ConflictException("Impossible de supprimer : des articles utilisent cette catégorie");
        }
        categorieRepository.delete(c);
    }

    private Categorie getOrThrow(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable : " + id));
    }

    private CategorieResponse toResponse(Categorie c) {
        return new CategorieResponse(c.getId(), c.getNom(), c.getDescription());
    }
}
