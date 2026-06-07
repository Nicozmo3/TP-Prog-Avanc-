# TP Spring REST - Gestion de Catalogue de Produits

## Description

Ce projet implémente une API REST pour la gestion d'un catalogue de produits en utilisant Spring Boot 3.2 et Java 17.

## Structure du Projet

```
tp-spring-rest/
├── pom.xml                                    # Configuration Maven
├── README.md                                 # Documentation
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── tpspringrest/
    │   │   │   └── TpSpringRestApplication.java  # Classe principale Spring Boot
    │   │   ├── catalogue/
    │   │   │   ├── Produit.java                     # Classe Produit (avec JAXB)
    │   │   │   ├── ICatalogueProduits.java          # Interface du service
    │   │   │   └── CatalogueProduits.java           # Implémentation du service (@Service)
    │   │   └── controleurs/rest/
    │   │       ├── ConfigREST.java                  # Configuration REST
    │   │       ├── CatalogueRestAPI.java            # Contrôleur REST principal
    │   │       ├── CatalogueRestAPIRequestScope.java # Contrôleur avec RequestScope
    │   │       ├── CatalogueRestAPISessionScope.java # Contrôleur avec SessionScope
    │   │       └── WebController.java               # Contrôleur pour servir les pages HTML
    │   └── resources/
    │       ├── application.properties              # Configuration Spring Boot
    │       └── static/
    │           ├── index.html                      # Page Web SPA
    │           ├── css/
    │           │   └── style.css                    # Styles CSS
    │           └── js/
    │               └── app.js                       # JavaScript/jQuery
    └── test/
        └── java/                                  # Tests (à compléter)
```

## Fonctionnalités Implémentées

### 1. Configuration de l'Application
- **Port HTTP** : 2020 (configuré dans `application.properties`)
- **Contexte de base** : `/api-rest` (configuré dans `application.properties`)
- **Java** : JDK 17
- **Framework** : Spring Boot 3.2

### 2. API REST

#### Endpoints disponibles :

| Méthode | URL | Description | Paramètres |
|---------|-----|-------------|------------|
| POST | `/api-rest/catalogue/produit` | Ajouter un produit (paramètres) | `pNom`, `pCateg` |
| POST | `/api-rest/catalogue/produit` | Ajouter un produit (JSON) | Body JSON |
| GET | `/api-rest/catalogue/produit/{numeroProd}` | Consulter un produit | `numeroProd` (path) |
| GET | `/api-rest/catalogue/produits` | Consulter tous les produits | - |
| DELETE | `/api-rest/catalogue/produit/{numeroProd}` | Supprimer un produit | `numeroProd` (path) |

#### Formats supportés :
- JSON (par défaut)
- XML (via JAXB annotations)

### 3. Négociation de Formats
- **Accept Header** : `application/json` ou `application/xml`
- **Content-Type Header** : `application/json` pour les requêtes POST avec body

### 4. Portée des Contrôleurs
- **Singleton** (par défaut) : `CatalogueRestAPI`
- **RequestScope** : `CatalogueRestAPIRequestScope` (URL: `/catalogue-request`)
- **SessionScope** : `CatalogueRestAPISessionScope` (URL: `/catalogue-session`)

### 5. Injection de Dépendances
- `CatalogueProduits` annoté avec `@Service`
- Injection dans les contrôleurs avec `@Autowired`
- Interface `ICatalogueProduits` pour le découplage

### 6. Interface Web (SPA)
- **Technologies** : HTML5, CSS3, JavaScript, jQuery, Bootstrap 5
- **Fonctionnalités** :
  - Consultation de tous les produits
  - Consultation d'un produit spécifique
  - Ajout de produit (paramètres ou JSON)
  - Suppression de produit
  - Sélection du format de réponse (JSON/XML)
  - Affichage sous forme de tableau

## Configuration

### application.properties
```properties
# Port HTTP
server.port=2020

# Contexte de base
server.servlet.context-path=/api-rest

# Configuration Jackson pour JSON
spring.jackson.serialization.indent_output=true

# Configuration Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/static/
spring.thymeleaf.suffix=.html
```

## Utilisation

### Avec cURL

```bash
# Ajouter un produit (paramètres)
curl -X POST "http://localhost:2020/api-rest/catalogue/produit?pNom=Ordinateur&pCateg=Électronique"

# Ajouter un produit (JSON)
curl -X POST http://localhost:2020/api-rest/catalogue/produit \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"nom":"Téléphone","categorie":"Électronique"}'

# Consulter un produit
curl -X GET http://localhost:2020/api-rest/catalogue/produit/1 \
  -H "Accept: application/json"

# Consulter tous les produits
curl -X GET http://localhost:2020/api-rest/catalogue/produits \
  -H "Accept: application/json"

# Supprimer un produit
curl -X DELETE http://localhost:2020/api-rest/catalogue/produit/1
```

### Avec Postman
1. Importer la collection de requêtes (à créer)
2. Configurer les headers :
   - `Accept`: `application/json` ou `application/xml`
   - `Content-Type`: `application/json` (pour les POST avec body)

### Interface Web
1. Démarrer l'application : `mvn spring-boot:run`
2. Ouvrir le navigateur : `http://localhost:2020/`
3. Utiliser l'interface pour interagir avec l'API

## Exécution

### Prérequis
- Java JDK 17
- Maven 3.6+

### Compilation
```bash
mvn clean compile
```

### Exécution
```bash
mvn spring-boot:run
```

### Build et Exécution
```bash
mvn clean package
java -jar target/tp-spring-rest-0.0.1-SNAPSHOT.jar
```

## Tests

### Tests manuels avec Postman
1. **Ajout de produit** : POST `/api-rest/catalogue/produit?pNom=Test&pCateg=Test`
2. **Consultation** : GET `/api-rest/catalogue/produit/1`
3. **Liste** : GET `/api-rest/catalogue/produits`
4. **Suppression** : DELETE `/api-rest/catalogue/produit/1`

### Tests avec l'interface Web
1. Ouvrir `http://localhost:2020/`
2. Tester toutes les fonctionnalités via l'interface

## Architecture

### Modèle MVC
- **Modèle** : `CatalogueProduits`, `Produit` (couche métier)
- **Vue** : `index.html`, `style.css`, `app.js` (interface utilisateur)
- **Contrôleur** : `CatalogueRestAPI` (API REST), `WebController` (pages HTML)

### Découplage
- Interface `ICatalogueProduits` pour le service
- Injection de dépendances avec `@Autowired`
- Annotations Spring : `@Service`, `@RestController`, `@Controller`

### Persistance
- En mémoire (ArrayList) pour ce TP
- Peut être étendu à Spring Data JPA pour une base de données

## Auteurs
- Basé sur le TP de Programmation Avancée (R5.A.05)
- IUT de Paris - Rives de Seine
- Université Paris Cité

## Documentation
- JavaEE : https://docs.oracle.com/javaee/7
- Spring Boot : https://spring.io/projects/spring-boot
