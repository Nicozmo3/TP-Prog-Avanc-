# TP 3.5 - Spring Security - Application Bancaire

## Description
Ce projet est une application Spring Boot sécurisée avec Spring Security. Il implémente une application bancaire simplifiée avec authentification et autorisation.

## Prérequis
- Java JDK 21 ou supérieur
- Maven 3.8.x ou supérieur
- MySQL Server
- Postman (pour les tests API)

## Configuration

### Base de données MySQL
1. Créer une base de données nommée `banque_db`
2. Mettre à jour les informations de connexion dans `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/banque_db
   spring.datasource.username=votre_utilisateur
   spring.datasource.password=votre_mot_de_passe
   ```

### Utilisateurs par défaut
L'application crée automatiquement deux utilisateurs pour les tests :
- **toto** / **toto123** - Rôle: USER
- **tintin** / **tintin123** - Rôle: ADMIN

## Structure du projet

```
tp3.5-spring-secu-banque/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── api/rest/
│   │   │   │   └── RestSimple.java          # Contrôleur REST de test
│   │   │   ├── banque/
│   │   │   │   ├── dao/                     # Repositories bancaires
│   │   │   │   ├── metier/                  # Entités et services bancaires
│   │   │   │   └── web/                     # Contrôleurs REST bancaires
│   │   │   ├── init/                        # Initialisation des données
│   │   │   ├── security/                    # Configuration de sécurité
│   │   │   │   ├── entities/                # Entité UserAccounts
│   │   │   │   ├── repository/              # Repository UserAccounts
│   │   │   │   └── MySQLdbUserDetailsService.java
│   │   │   └── fr/iut/tp35springsecubanque/  # Classe principale
│   │   └── resources/
│   │       └── application.properties       # Configuration
│   └── test/
└── pom.xml
```

## Endpoints disponibles

### Endpoints de test (RestSimple)
- `GET /public` - Ressource publique (accessible à tous)
- `GET /protege/user` - Ressource réservée aux utilisateurs (rôle USER ou ADMIN)
- `GET /protege/admin` - Ressource réservée aux administrateurs (rôle ADMIN)

### Endpoints bancaires
- `POST /api/banque/clients` - Créer un client
- `GET /api/banque/clients` - Lister tous les clients
- `GET /api/banque/clients/{id}` - Obtenir un client par ID
- `GET /api/banque/clients/email/{email}` - Obtenir un client par email
- `DELETE /api/banque/clients/{id}` - Supprimer un client

- `POST /api/banque/comptes` - Créer un compte
- `GET /api/banque/comptes` - Lister tous les comptes
- `GET /api/banque/comptes/{id}` - Obtenir un compte par ID
- `GET /api/banque/comptes/numero/{numero}` - Obtenir un compte par numéro
- `GET /api/banque/clients/{clientId}/comptes` - Obtenir les comptes d'un client
- `DELETE /api/banque/comptes/{id}` - Supprimer un compte

- `POST /api/banque/comptes/{numero}/verser?montant={montant}` - Verser sur un compte
- `POST /api/banque/comptes/{numero}/retirer?montant={montant}` - Retirer d'un compte
- `POST /api/banque/virement?source={source}&destination={destination}&montant={montant}` - Effectuer un virement

## Tests avec Postman

### Configuration de l'authentification Basic Auth
1. Ouvrir Postman
2. Sélectionner la requête (GET, POST, etc.)
3. Aller dans l'onglet "Authorization"
4. Sélectionner "Basic Auth" dans le menu déroulant
5. Entrer le username et password:
   - Pour le rôle USER: `toto` / `toto123`
   - Pour le rôle ADMIN: `tintin` / `tintin123`

### Exemples de requêtes

#### Accès à la ressource publique
```
GET http://localhost:8080/public
```
Pas besoin d'authentification.

#### Accès à la ressource utilisateur
```
GET http://localhost:8080/protege/user
```
Nécessite l'authentification avec le rôle USER ou ADMIN.

#### Accès à la ressource administrateur
```
GET http://localhost:8080/protege/admin
```
Nécessite l'authentification avec le rôle ADMIN.

#### Créer un client
```
POST http://localhost:8080/api/banque/clients
Content-Type: application/json

{
    "nom": "Durand",
    "prenom": "Pierre",
    "email": "pierre.durand@email.com",
    "telephone": "0612345678"
}
```
Nécessite l'authentification avec le rôle ADMIN.

#### Effectuer un virement
```
POST http://localhost:8080/api/banque/virement?source=CPT001&destination=CPT002&montant=100
```
Nécessite l'authentification.

## Configuration de la sécurité

La configuration de sécurité est définie dans deux classes :

1. **SecuConfig.java** - Configuration avec utilisateurs en mémoire
2. **SecuConfigDB.java** - Configuration avec base de données

Pour utiliser la configuration avec base de données, commentez l'annotation `@Configuration` dans `SecuConfig.java` et décommentez celle dans `SecuConfigDB.java`.

## Construction et exécution

### Construire le projet
```bash
cd tp3.5-spring-secu-banque
mvn clean package
```

### Exécuter l'application
```bash
mvn spring-boot:run
```

L'application sera disponible à l'adresse: http://localhost:8080

## Journalisation

Les logs de démarrage affichent les informations de connexion par défaut si vous utilisez la configuration Spring Security par défaut (avant d'activer la configuration personnalisée).

## Sécurité implémentée

- **Authentification** : Basic Auth et Form Login
- **Autorisation** : Basée sur les rôles (USER, ADMIN)
- **Chiffrement des mots de passe** : BCryptPasswordEncoder
- **Protection CSRF** : Désactivée pour les tests avec Postman (à activer en production)
- **Gestion des sessions** : Configurée par défaut

## Notes

- Les mots de passe sont chiffrés avec BCrypt
- Les utilisateurs sont stockés en base de données MySQL
- L'application initialise automatiquement des données de test au démarrage
- Pour utiliser la base de données, assurez-vous que MySQL est en cours d'exécution
