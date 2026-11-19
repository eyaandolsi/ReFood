package tn.esprit.refood.offers.service;

import tn.esprit.refood.offers.entity.Paiement;

import java.util.List;

public interface PaiementService {
    Paiement createPaiement(Paiement paiement);
    Paiement getPaiementById(Long id);
    List<Paiement> getAllPaiements();
    Paiement updatePaiement(Long id, Paiement paiement);
    void deletePaiement(Long id);
}
