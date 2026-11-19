package tn.esprit.refood.recipes.service;

import tn.esprit.refood.recipes.exception.ResourceNotFoundException;
import tn.esprit.refood.recipes.model.Favori;
import tn.esprit.refood.recipes.model.Recette;
import tn.esprit.refood.recipes.repository.FavoriRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FavoriService {

    private final FavoriRepository favoriRepository;
    private final RecetteService recetteService;

    public FavoriService(FavoriRepository favoriRepository, RecetteService recetteService) {
        this.favoriRepository = favoriRepository;
        this.recetteService = recetteService;
    }

    @Transactional(readOnly = true)
    public List<Favori> listerParUtilisateur(String utilisateurId) {
        return favoriRepository.findByUtilisateurIdOrderBySauvegardeLeDesc(utilisateurId);
    }

    public Favori ajouter(String utilisateurId, Long recetteId, Integer note) {
        if (favoriRepository.existsByUtilisateurIdAndRecetteId(utilisateurId, recetteId)) {
            throw new IllegalArgumentException("Cette recette est deja dans les favoris de l'utilisateur");
        }
        Recette recette = recetteService.trouver(recetteId);
        Favori favori = new Favori();
        favori.setUtilisateurId(utilisateurId);
        favori.setRecette(recette);
        favori.setNote(note);
        return favoriRepository.save(favori);
    }

    public Favori noter(Long favoriId, Integer note) {
        Favori favori = favoriRepository.findById(favoriId)
                .orElseThrow(() -> new ResourceNotFoundException("Favori introuvable : " + favoriId));
        favori.setNote(note);
        return favoriRepository.save(favori);
    }

    public void retirer(String utilisateurId, Long recetteId) {
        Favori favori = favoriRepository.findByUtilisateurIdAndRecetteId(utilisateurId, recetteId)
                .orElseThrow(() -> new ResourceNotFoundException("Favori introuvable pour cette recette"));
        favoriRepository.delete(favori);
    }
}