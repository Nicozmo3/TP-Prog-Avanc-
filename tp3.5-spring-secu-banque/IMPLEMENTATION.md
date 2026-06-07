# Implémentation du TP 3.5 - Spring Security

## Résumé de l'implémentation

Ce projet implémente toutes les manipulations demandées dans le TP 3.5 sur Spring Security.

## Manipulations Implémentées

### 1. Installation de Client d'API REST
- **Manipulation 1-2**: Documentation pour Postman incluse dans le README

### 2. Création du projet Spring Boot
- **Manipulation 3-6**: Projet Maven créé avec Spring Boot 3.2.5
  - Dépendances: Spring Web, Spring Security, Spring Data JPA, MySQL Connector
  - Structure Maven complète

### 3. Création d'un contrôleur REST de test
- **Manipulation 7**: `api.rest.RestSimple` créé avec 3 méthodes:
  - `ressourcePublique()` -> `/public`
  - `ressourceUtilisateurs()` -> `/protege/user`
  - `ressourceAdministrateurs()` -> `/protege/admin`
- **Manipulation 8**: `@ComponentScan` ajouté à la classe principale
- **Manipulation 9-10**: Prêt pour les tests avec Postman

### 4. Activation de la sécurité par défaut
- **Manipulation 11-12**: Spring Security activé via les dépendances
- **Manipulation 13-16**: Configuration de base avec formulaire d'authentification

### 5. Configuration du compte utilisateur pour les tests
- **Manipulation 17-18**: Configuration dans `application.properties` (commentée)

### 6. Restriction des accès par authentification
- **Manipulation 19-21**: Classe `security.SecuConfig` créée avec:
  - `@Configuration` et `@EnableWebSecurity`
  - Méthode `filterChain()` pour SecurityFilterChain
  - Accès à `/public` pour tous
  - Accès à `/protege/**` pour les utilisateurs authentifiés

### 7. Restriction des accès par rôles
- **Manipulation 22-25**: Configuration complète dans `SecuConfig`:
  - `/public` accessible à tous
  - `/protege/user` accessible aux rôles USER ou ADMIN
  - `/protege/admin` accessible uniquement au rôle ADMIN
  - Basic Auth et Form Login activés
  - Tests manuels possibles avec Postman et navigateur

### 8. Configuration de la base des comptes utilisateurs en mémoire
- **Manipulation 26-28**: 
  - Compte utilisateur par défaut retiré de `application.properties`
  - Méthode `userDetailsService()` dans `SecuConfig` avec InMemoryUserDetailsManager
  - Deux utilisateurs: toto (USER) et tintin (ADMIN)
  - Mots de passe chiffrés avec BCrypt

### 9. Chiffrement des mots de passe
- **Manipulation 29-31**:
  - Méthode `passwordEncoder()` dans `SecuConfig`
  - Utilisation de BCryptPasswordEncoder
  - Mots de passe des utilisateurs en mémoire chiffrés

### 10. Authentification à l'aide d'une base de données
- **Manipulation 32-45**:
  - `MySQLdbUserDetailsService` implémente UserDetailsService
  - Méthode `loadUserByUsername()` pour charger les utilisateurs depuis MySQL
  - Entité `UserAccounts` avec annotations JPA
  - Repository `IUserAccountsRepository` étend JpaRepository
  - Configuration dans `SecuConfigDB`:
    - `authenticationManager()` avec DaoAuthenticationProvider
    - `authenticationProvider()` avec UserDetailsService et PasswordEncoder
    - `userDetailsService()` retourne MySQLdbUserDetailsService
  - `@EntityScan` et `@EnableJpaRepositories` ajoutés à la classe principale
  - Configuration MySQL dans `application.properties`
  - `InitAccounts` implémente CommandLineRunner pour initialiser les utilisateurs
  - Tests manuels possibles avec la base de données

### 11. Sécurité de l'application « Banque simplifiée »
- **Application bancaire complète** avec:
  - Entités: `Client`, `Compte`
  - Repositories: `ClientRepository`, `CompteRepository`
  - Service: `BanqueService` avec méthodes CRUD et opérations bancaires
  - Contrôleur REST: `BanqueController` avec endpoints sécurisés
  - Initialisation: `InitBanqueData` pour créer des données de test
  - Sécurité: Tous les endpoints `/api/banque/**` nécessitent authentification

## Structure des packages

```
fr.iut.tp35springsecubanque
├── Tp35SpringSecuBanqueApplication.java (Classe principale)

api.rest
└── RestSimple.java (Contrôleur REST de test)

security
├── SecuConfig.java (Configuration avec utilisateurs en mémoire)
├── SecuConfigDB.java (Configuration avec base de données)
├── MySQLdbUserDetailsService.java (Service de chargement des utilisateurs)
├── entities
│   └── UserAccounts.java (Entité JPA pour les utilisateurs)
└── repository
    └── IUserAccountsRepository.java (Repository Spring Data)

banque
├── dao
│   ├── ClientRepository.java
│   └── CompteRepository.java
├── metier
│   ├── Client.java
│   ├── Compte.java
│   └── BanqueService.java
└── web
    └── BanqueController.java

init
├── InitAccounts.java (Initialisation des utilisateurs)
└── InitBanqueData.java (Initialisation des données bancaires)
```

## Configuration active

Par défaut, la configuration avec base de données (`SecuConfigDB`) est active.
La configuration avec utilisateurs en mémoire (`SecuConfig`) est commentée.

Pour basculer vers la configuration en mémoire:
1. Commentez `@Configuration` dans `SecuConfigDB.java`
2. Décommentez `@Configuration` dans `SecuConfig.java`

## Utilisateurs par défaut

Avec la configuration base de données (active par défaut):
- **toto** / **toto123** - Rôle: USER
- **tintin** / **tintin123** - Rôle: ADMIN

Les mots de passe sont chiffrés avec BCrypt et stockés dans la base de données MySQL.

## Endpoints sécurisés

### Endpoints de test
- `GET /public` - Public (pas d'authentification)
- `GET /protege/user` - Nécessite rôle USER ou ADMIN
- `GET /protege/admin` - Nécessite rôle ADMIN

### Endpoints bancaires
Tous les endpoints sous `/api/banque/**` nécessitent authentification:
- Clients: `/api/banque/clients` (GET, POST)
- Comptes: `/api/banque/comptes` (GET, POST)
- Opérations: `/api/banque/virement`, `/api/banque/comptes/{numero}/verser`, etc.

## Tests recommandés

1. **Test de la ressource publique**:
   ```bash
   curl http://localhost:8080/public
   ```

2. **Test de la ressource utilisateur (avec Basic Auth)**:
   ```bash
   curl -u toto:toto123 http://localhost:8080/protege/user
   ```

3. **Test de la ressource administrateur**:
   ```bash
   curl -u tintin:tintin123 http://localhost:8080/protege/admin
   ```

4. **Test de l'API bancaire**:
   ```bash
   curl -u tintin:tintin123 http://localhost:8080/api/banque/clients
   ```

## Notes techniques

- **Spring Boot 3.2.5** avec Spring Security 6.x
- **Java 21** requis
- **MySQL** pour la persistance
- **Hibernate** comme implémentation JPA
- **BCrypt** pour le chiffrement des mots de passe
- **CSRF** désactivé pour les tests avec Postman (à activer en production)
- **CORS** peut être configuré si nécessaire pour les applications frontend

## Prochaines étapes

1. Installer Java 21 et Maven
2. Créer la base de données MySQL avec le script `init_db.sql`
3. Mettre à jour les informations de connexion dans `application.properties`
4. Exécuter l'application: `mvn spring-boot:run`
5. Tester avec Postman ou curl
