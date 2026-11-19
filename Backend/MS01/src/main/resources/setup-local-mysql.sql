-- Script pour configurer MySQL local pour MS01
-- Exécuter ce script dans MySQL Workbench ou via ligne de commande

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS refood_offers CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Si vous voulez utiliser l'utilisateur 'refood' avec mot de passe 'refood' (optionnel)
-- CREATE USER IF NOT EXISTS 'refood'@'localhost' IDENTIFIED BY 'refood';
-- GRANT ALL PRIVILEGES ON refood_offers.* TO 'refood'@'localhost';
-- FLUSH PRIVILEGES;

-- Si vous utilisez l'utilisateur 'root' (déjà configuré dans application.properties)
-- Aucune commande supplémentaire n'est nécessaire

-- Les tables seront créées automatiquement par Hibernate au démarrage de l'application
