package tn.esprit.refood.offers.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.refood.offers.entity.Categorie;
import tn.esprit.refood.offers.exception.BadRequestException;
import tn.esprit.refood.offers.exception.ResourceNotFoundException;
import tn.esprit.refood.offers.repository.CategorieRepository;
import tn.esprit.refood.offers.service.CategorieService;

import java.util.List;

/**
 * Implémentation du service pour la gestion des catégories.
 * 
 * Ce service gère les opérations CRUD de base sur les catégories.
 * Une catégorie peut avoir plusieurs offres (relation OneToMany).
 * Le nom de la catégorie est unique (contrainte unique en base).
 * 
 * @Service : Composant Spring géré par le conteneur IoC.
 * @Transactional : Gestion des transactions automatique.
 */
@Service
@Transactional
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    /**
     * Injection par constructeur.
     */
    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    /**
     * Créer une nouvelle catégorie.
     */
    @Override
    public Categorie createCategorie(Categorie categorie) {
        if (categorie == null) {
            throw new BadRequestException("La catégorie ne peut pas être null");
        }
        if (categorie.getNom() == null || categorie.getNom().trim().isEmpty()) {
            throw new BadRequestException("Le nom de la catégorie est obligatoire");
        }
        return categorieRepository.save(categorie);
    }

    /**
     * Récupérer une catégorie par son ID.
     */
    @Override
    @Transactional(readOnly = true)
    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie", id));
    }

    /**
     * Récupérer toutes les catégories.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    /**
     * Mettre à jour une catégorie.
     */
    @Override
    public Categorie updateCategorie(Long id, Categorie categorie) {
        if (categorie == null) {
            throw new BadRequestException("Les données de la catégorie sont obligatoires");
        }
        
        Categorie categorieExistante = getCategorieById(id);
        
        if (categorie.getNom() != null && !categorie.getNom().trim().isEmpty()) {
            categorieExistante.setNom(categorie.getNom());
        }
        if (categorie.getDescription() != null) {
            categorieExistante.setDescription(categorie.getDescription());
        }
        
        return categorieRepository.save(categorieExistante);
    }

    /**
     * Supprimer une catégorie.
     */
    @Override
    public void deleteCategorie(Long id) {
        Categorie categorie = getCategorieById(id);
        categorieRepository.delete(categorie);
    }
}
