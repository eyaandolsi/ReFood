-- Script pour nettoyer la base de données refood_offers
-- Exécuter ce script dans MySQL Workbench avant de démarrer l'application

USE refood_offers;

-- Désactiver les vérifications de clés étrangères
SET FOREIGN_KEY_CHECKS = 0;

-- Supprimer toutes les tables dans le bon ordre (en tenant compte des dépendances)
DROP TABLE IF EXISTS paiement;
DROP TABLE IF EXISTS commande;
DROP TABLE IF EXISTS offre;
DROP TABLE IF EXISTS categorie;
DROP TABLE IF EXISTS donateur;
DROP TABLE IF EXISTS beneficiaire;

-- Réactiver les vérifications de clés étrangères
SET FOREIGN_KEY_CHECKS = 1;
