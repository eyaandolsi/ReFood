
package tn.esprit.refood.offers.service;

import tn.esprit.refood.offers.entity.Offre;

import java.util.List;

public interface OffreService {

    // Opérations CRUD
    Offre createOffre(Offre offre);

    Offre getOffreById(Long id);

    List<Offre> getAllOffres();

    Offre updateOffre(Long id, Offre offre);

    void deleteOffre(Long id);

    // Opérations métier selon le diagramme UML
    Offre publier(Long id);

    Offre reserver(Long id, Integer quantite);

    Offre liberer(Long id, Integer quantite);

    Offre desactiver(Long id);

    Offre cloturer(Long id);
}
