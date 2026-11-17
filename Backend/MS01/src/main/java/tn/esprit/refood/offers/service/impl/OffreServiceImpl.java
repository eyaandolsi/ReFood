
package tn.esprit.refood.offers.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.refood.offers.entity.Offre;
import tn.esprit.refood.offers.entity.StatutOffre;
import tn.esprit.refood.offers.repository.OffreRepository;
import tn.esprit.refood.offers.service.OffreService;

import java.util.List;

/**
 * Implémentation du service pour la gestion des offres.
 * 
 * Cette classe contient la logique métier pour les opérations sur les offres.
 * Elle utilise le repository pour accéder aux données et applique les règles métier.
 * 
 * @Service : Indique à Spring que cette classe est un composant de service.
 * @Transactional : Garantit que toutes les méthodes s'exécutent dans une transaction.
 *                Si une méthode lance une exception, la transaction est rollbackée.
 *                C'est essentiel pour la cohérence des données.
 */
@Service
@Transactional
public class OffreServiceImpl implements OffreService {

    // Repository pour l'accès aux données (couche DAO)
    private final OffreRepository offreRepository;

    /**
     * Injection par constructeur.
     * 
     * L'injection par constructeur est préférée car:
     * - Elle permet de déclarer le repository comme 'final' (immutabilité)
     * - Elle garantit que le repository est toujours présent (pas de null)
     * - Elle facilite les tests unitaires (on peut facilement mocker le repository)
     */
    public OffreServiceImpl(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    /**
     * Créer une nouvelle offre.
     * 
     * Cette méthode valide les données avant de sauvegarder.
     * La validation au niveau service est importante car:
     * - Elle permet d'appliquer des règles métier complexes
     * - Elle évite de sauvegarder des données invalides en base
     * - Elle centralise la logique de validation
     * 
     * @throws IllegalArgumentException si les données sont invalides
     */
    @Override
    public Offre createOffre(Offre offre) {

        // Validation: l'offre ne peut pas être null
        if (offre == null) {
            throw new IllegalArgumentException("L'offre ne peut pas être null");
        }

        // Validation: la quantité doit être positive
        if (offre.getQuantite() < 0) {
            throw new IllegalArgumentException(
                    "La quantité ne peut pas être négative"
            );
        }

        // Sauvegarde en base de données (la transaction est gérée par @Transactional)
        return offreRepository.save(offre);
    }

    /**
     * Récupérer une offre par son ID.
     * 
     * @Transactional(readOnly = true) : Optimisation pour les lectures.
     * - Indique à Hibernate/JPA qu'il s'agit d'une opération en lecture seule
     * - Permet des optimisations de performance (pas de dirty checking)
     * - Évite de verrouiller inutilement les enregistrements
     * 
     * @throws IllegalArgumentException si l'offre n'existe pas
     */
    @Override
    @Transactional(readOnly = true)
    public Offre getOffreById(Long id) {

        return offreRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Offre introuvable avec ID : " + id
                        )
                );
    }

    /**
     * Récupérer toutes les offres.
     * 
     * @Transactional(readOnly = true) : Optimisation pour les lectures.
     * 
     * Note: En production, cette méthode devrait inclure:
     * - De la pagination (pour éviter de charger trop de données)
     * - Des filtres (par statut, par catégorie, par date)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Offre> getAllOffres() {

        return offreRepository.findAll();
    }

    /**
     * Mettre à jour une offre existante.
     * 
     * Cette méthode utilise le pattern "update by merge":
     * 1. Récupérer l'offre existante (pour s'assurer qu'elle existe)
     * 2. Mettre à jour les champs un par un (contrôle précis)
     * 3. Sauvegarder les modifications
     * 
     * Avantages par rapport à un simple save():
     * - On ne peut pas créer une nouvelle offre par erreur
     * - On contrôle exactement quels champs sont modifiés
     * - On peut ajouter des validations spécifiques par champ
     */
    @Override
    public Offre updateOffre(Long id, Offre nouvelleOffre) {

        // Récupérer l'offre existante (lance une exception si elle n'existe pas)
        Offre offreExistante = getOffreById(id);

        // Validation des données
        if (nouvelleOffre == null) {
            throw new IllegalArgumentException(
                    "Les données de l'offre sont obligatoires"
            );
        }

        // Mise à jour des champs
        offreExistante.setTitre(nouvelleOffre.getTitre());
        offreExistante.setDescription(nouvelleOffre.getDescription());
        offreExistante.setTypeOffre(nouvelleOffre.getTypeOffre());
        offreExistante.setQuantite(nouvelleOffre.getQuantite());
        offreExistante.setDateExpiration(nouvelleOffre.getDateExpiration());
        offreExistante.setStatutOffre(nouvelleOffre.getStatutOffre());

        // Sauvegarde des modifications
        return offreRepository.save(offreExistante);
    }

    /**
     * Publier une offre (changer son statut à DISPONIBLE).
     * 
     * C'est une opération métier spécifique qui suit le workflow:
     * OFFRE créée -> PUBLIÉE (DISPONIBLE) -> RÉSERVÉE -> EXPIRÉE/ANNULÉE
     * 
     * On empêche de republier une offre annulée pour maintenir
     * la cohérence du workflow métier.
     */
    @Override
    public Offre publier(Long id) {

        Offre offre = getOffreById(id);

        // Règle métier: on ne peut pas republier une offre annulée
        if (offre.getStatutOffre() == StatutOffre.ANNULEE) {
            throw new IllegalStateException(
                    "Impossible de publier une offre annulée"
            );
        }

        // Changement de statut
        offre.setStatutOffre(StatutOffre.DISPONIBLE);

        return offreRepository.save(offre);
    }

    /**
     * Réserver une quantité d'une offre.
     * 
     * Cette méthode implémente la logique de réservation:
     * 1. Vérifier que l'offre est disponible
     * 2. Vérifier qu'il y a assez de stock
     * 3. Déduire la quantité et changer le statut
     * 
     * Ces validations sont critiques pour éviter:
     * - Les réservations sur des offres non disponibles
     * - Les sur-réservations (réserver plus que le stock)
     */
    @Override
    public Offre reserver(Long id, Integer quantite) {

        Offre offre = getOffreById(id);

        // Validation: la quantité doit être positive
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException(
                    "La quantité doit être supérieure à zéro"
            );
        }

        // Règle métier: on ne peut réserver que des offres disponibles
        if (offre.getStatutOffre() != StatutOffre.DISPONIBLE) {
            throw new IllegalStateException(
                    "Cette offre n'est pas disponible"
            );
        }

        // Règle métier: vérifier le stock disponible
        if (offre.getQuantite() < quantite) {
            throw new IllegalArgumentException(
                    "Quantité disponible insuffisante"
            );
        }

        // Mise à jour du stock et du statut
        offre.setQuantite(offre.getQuantite() - quantite);
        offre.setStatutOffre(StatutOffre.RESERVEE);

        return offreRepository.save(offre);
    }

    /**
     * Libérer une quantité réservée (annulation de réservation).
     * 
     * Cette méthode est utilisée quand une réservation est annulée.
     * Elle remet la quantité en stock et rend l'offre disponible à nouveau.
     * 
     * Note: Dans une implémentation plus avancée, on pourrait:
     * - Tracer qui a libéré la réservation
     * - Ajouter des logs d'audit
     * - Envoyer des notifications
     */
    @Override
    public Offre liberer(Long id, Integer quantite) {

        Offre offre = getOffreById(id);

        // Validation: la quantité doit être positive
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException(
                    "La quantité doit être supérieure à zéro"
            );
        }

        // Remise en stock et changement de statut
        offre.setQuantite(offre.getQuantite() + quantite);
        offre.setStatutOffre(StatutOffre.DISPONIBLE);

        return offreRepository.save(offre);
    }

    /**
     * Désactiver une offre (changer son statut à ANNULEE).
     * 
     * Cette opération est utilisée pour annuler une offre.
     * Une fois annulée, l'offre ne peut plus être publiée (voir méthode publier).
     */
    @Override
    public Offre desactiver(Long id) {

        Offre offre = getOffreById(id);

        offre.setStatutOffre(StatutOffre.ANNULEE);

        return offreRepository.save(offre);
    }

    /**
     * Clôturer une offre (changer son statut à EXPIRÉE).
     * 
     * Cette opération est utilisée quand une offre expire
     * (date d'expiration dépassée ou stock épuisé).
     */
    @Override
    public Offre cloturer(Long id) {

        Offre offre = getOffreById(id);

        offre.setStatutOffre(StatutOffre.EXPIREE);

        return offreRepository.save(offre);
    }

    /**
     * Supprimer physiquement une offre de la base de données.
     * 
     * Cette méthode effectue une suppression physique (DELETE).
     * En production, on préfère souvent une suppression logique (soft delete)
     * en ajoutant un champ 'deleted' ou 'actif' pour:
     * - Garder un historique des offres
     * - Permettre la restauration en cas d'erreur
     * - Maintenir l'intégrité des données liées (commandes, paiements)
     * 
     * Note: La suppression supprimera aussi les commandes liées (cascade=ALL).
     */
    @Override
    public void deleteOffre(Long id) {

        Offre offre = getOffreById(id);

        offreRepository.delete(offre);
    }
}
