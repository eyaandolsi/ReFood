package tn.esprit.refood.offers.service;

import tn.esprit.refood.offers.entity.Categorie;

import java.util.List;

public interface CategorieService {
    Categorie createCategorie(Categorie categorie);
    Categorie getCategorieById(Long id);
    List<Categorie> getAllCategories();
    Categorie updateCategorie(Long id, Categorie categorie);
    void deleteCategorie(Long id);
}
