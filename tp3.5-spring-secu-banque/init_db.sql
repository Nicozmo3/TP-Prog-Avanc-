-- Script SQL pour initialiser la base de données MySQL
-- Exécuter ce script dans MySQL avant de démarrer l'application

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS banque_db;
USE banque_db;

-- Créer la table des utilisateurs
CREATE TABLE IF NOT EXISTS user_accounts (
    login VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    roles VARCHAR(100) NOT NULL
);

-- Créer les tables bancaires
CREATE TABLE IF NOT EXISTS clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telephone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS comptes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(50) NOT NULL UNIQUE,
    solde DOUBLE NOT NULL,
    date_creation DATE,
    client_id BIGINT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

-- Insérer des utilisateurs de test (les mots de passe seront chiffrés par l'application)
-- Ces utilisateurs seront créés automatiquement par l'application au démarrage
-- via la classe InitAccounts.java

-- Insérer des données bancaires de test (optionnel)
-- Ces données seront créées automatiquement par l'application au démarrage
-- via la classe InitBanqueData.java

-- Afficher les tables créées
SHOW TABLES;
