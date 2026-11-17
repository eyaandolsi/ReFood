package tn.esprit.refood.offers.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.refood.offers.entity.Categorie;
import tn.esprit.refood.offers.repository.CategorieRepository;
import tn.esprit.refood.offers.service.CategorieService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service pour la gestion des catégories.
 * 
 * Ce service gère les opérations CRUD de base sur les catégories.
 * Une catégorie peut avoir plusieurs offres (relation OneToMany).
 * Le nom de la catégorie est unique (contrainte unique en base).
 * 
 * Note: Cette implémentation est basique. En production, on pourrait ajouter:
 * - Validation du nom (ne doit pas être vide, doit être unique)
 * - Gestion des transactions avec @Transactional
 * - Empêcher la suppression si des offres sont liées
 * 
 * @Service : Composant Spring géré par le conteneur IoC.
 */
@Service
public class CategorieServiceImpl implements CategorieService {

    // TODO: Remplacer @Autowired par injection par constructeur
    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Créer une nouvelle catégorie.
     * 
     * Note: Ajouter des validations:
     * - Vérifier que le nom n'est pas vide
     * - Vérifier que le nom n'existe pas déjà
     */
    @Override
    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    /**
     * Récupérer une catégorie par son ID.
     * 
     * Retourne null si la catégorie n'existe pas.
     */
    @Override
    public Categorie getCategorieById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return categorie.orElse(null);
    }

    /**
     * Récupérer toutes les catégories.
     * 
     * Note: Les offres associées ne sont pas chargées par défaut (lazy loading)
     * pour éviter les problèmes de performance N+1.
     */
    @Override
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    /**
     * Mettre à jour une catégorie.
     * 
     * Note: Si on modifie le nom, il faut vérifier qu'il n'existe pas déjà.
     */
    @Override
    public Categorie updateCategorie(Long id, Categorie categorie) {
        if (categorieRepository.existsById(id)) {
            categorie.setId(id);
            return categorieRepository.save(categorie);
        }
        return null;
    }

    /**
     * Supprimer une catégorie.
     * 
     * Attention: La suppression supprimera aussi toutes les offres associées
     * (cascade=ALL dans l'entité). En production, vérifier d'abord
     * qu'il n'y a pas d'offres liées.
     */
    @Override
    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }
}
