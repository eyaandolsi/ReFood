package tn.esprit.refood.offers.service;

import tn.esprit.refood.offers.entity.Commande;

import java.util.List;

public interface CommandeService {
    Commande createCommande(Commande commande);
    Commande getCommandeById(Long id);
    List<Commande> getAllCommandes();
    Commande updateCommande(Long id, Commande commande);
    void deleteCommande(Long id);
}
