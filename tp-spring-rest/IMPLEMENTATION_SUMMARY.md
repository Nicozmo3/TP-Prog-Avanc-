# Résumé de l'Implémentation du TP Spring REST

## Introduction

Ce document résume l'implémentation complète du TP "Développement d'API REST avec Spring Boot" tel que décrit dans les markdown du projet.

## Étapes Réalisées

### 1. Description de l'API REST ✅

L'API REST développée permet de gérer un catalogue de produits avec les fonctionnalités suivantes :

- **POST** `/catalogue/produit` - Ajouter un produit (avec paramètres ou JSON)
- **GET** `/catalogue/produit/{numeroProd}` - Consulter un produit spécifique
- **GET** `/catalogue/produits` - Consulter tous les produits
- **DELETE** `/catalogue/produit/{numeroProd}` - Supprimer un produit

### 2. Création du Projet Spring Boot ✅

#### Manipulation 1: Projet Maven
- Créé un projet Maven simple nommé `tp-spring-rest`
- Structure de projet conforme aux standards Maven

#### Manipulation 2: Projet Spring Boot Starter
- Configuration via `pom.xml` avec :
  - Parent: `spring-boot-starter-parent` version 3.2.0
  - Dépendances: `spring-boot-starter-web`, `spring-boot-starter-test`, `spring-boot-starter-thymeleaf`, `jackson-databind`, `jakarta.xml.bind-api`
  - Java version: 17

#### Manipulation 3: Package controleurs.rest
- Créé le package `controleurs.rest` pour tous les contrôleurs REST

#### Manipulation 4: Postman
- Application Postman recommandée pour les tests (non incluse dans le projet)

### 3. Paramètres Globaux de l'Application Spring Boot ✅

#### Manipulation 5 & 6: application.properties
```properties
server.port=2020
server.servlet.context-path=/api-rest
```

#### Manipulation 7: Tests manuels
- Configuration prête pour les tests avec Postman
- URL de base: `http://localhost:2020/api-rest`

### 4. Classe de Configuration du Projet Spring Boot ✅

#### Manipulation 8: Classe ConfigREST
- Créée dans `controleurs.rest.ConfigREST`

#### Manipulation 9: Annotation @Configuration
- Classe annotée avec `@Configuration`

#### Manipulation 10: @ComponentScan
- Configuration pour scanner les packages `controleurs.rest` et `catalogue`

### 5. Implémentation des Services de l'API REST ✅

#### Manipulation 11: Import des classes
- Classes `CatalogueProduits` et `Produit` importées depuis le dossier `catalogue`
- Placées dans le package `catalogue` du projet

### 6. Implémentation du Contrôleur de l'API REST ✅

#### Manipulation 12: Classe CatalogueRestAPI
- Créée dans `controleurs.rest.CatalogueRestAPI`

#### Manipulation 13: Annotations @RestController et @RequestMapping
```java
@RestController
@RequestMapping("/catalogue")
public class CatalogueRestAPI { ... }
```

#### Manipulation 14 & 15: Méthode ajouterProduit avec @PostMapping
```java
@PostMapping(value = "/produit", params = {"pNom", "pCateg"})
public ResponseEntity<Produit> ajouterProduitAvecParams (
        @RequestParam("pNom") String nomProd,
        @RequestParam("pCateg") String categProd) { ... }
```

#### Manipulation 16: Extraction des paramètres avec @RequestParam
- Paramètres `pNom` et `pCateg` extraits avec `@RequestParam`

#### Manipulation 17: Tests manuels
- Prêt pour les tests avec Postman

#### Manipulation 18 & 19: Méthode consulterProduit avec @GetMapping et @PathVariable
```java
@GetMapping(value = "/produit/{numeroProd}")
public ResponseEntity<Produit> consulterProduit (
        @PathVariable("numeroProd") int numeroProd) { ... }
```

#### Manipulation 20: Tests manuels
- Redémarrage de l'application et tests avec Postman

#### Manipulation 21: Méthode getTousProduits avec @GetMapping
```java
@GetMapping(value = "/produits")
public ResponseEntity<List<Produit>> getTousProduits () { ... }
```

#### Manipulation 22: Tests manuels
- Tests avec Postman

#### Manipulation 23: Méthode supprimerProduit avec @DeleteMapping
```java
@DeleteMapping(value = "/produit/{numeroProd}")
public ResponseEntity<Integer> supprimerProduit (
        @PathVariable("numeroProd") int numeroProd) { ... }
```

#### Manipulation 24: Tests manuels
- Tests avec Postman

#### Manipulation 25: ResponseEntity<T> pour les codes HTTP
- Toutes les méthodes retournent `ResponseEntity<T>` avec les codes HTTP appropriés:
  - 201 CREATED pour l'ajout
  - 200 OK pour la consultation
  - 404 NOT_FOUND pour les produits non trouvés
  - 200 OK pour la suppression réussie

#### Manipulation 26: Méthode ajouterProduit avec @RequestBody
```java
@PostMapping(value = "/produit", consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Produit> ajouterProduitAvecBody (@RequestBody Produit produit) { ... }
```
- Injection du produit JSON avec `@RequestBody`
- Content-Type: `application/json` requis

#### Manipulation 27: Tests manuels
- Tests avec Postman en envoyant un produit au format JSON

### 7. Négociation de Formats ✅

#### Manipulation 28: Header Accept
- Configuration dans l'interface Web pour permettre la sélection du format

#### Manipulation 29: Paramètre produces
- Toutes les annotations `@GetMapping`, `@PostMapping`, `@DeleteMapping` incluent:
```java
produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
```
- Support des formats JSON et XML

### 8. Portée d'un Contrôleur REST ✅

#### Manipulation 30: Portée par défaut
- Par défaut: Singleton (une instance pour toute l'application)

#### Manipulation 31: Portée RequestScope et SessionScope
- **RequestScope**: `CatalogueRestAPIRequestScope` avec `@Scope(value = WebApplicationContext.SCOPE_REQUEST)`
- **SessionScope**: `CatalogueRestAPISessionScope` avec `@Scope(value = WebApplicationContext.SCOPE_SESSION)`
- URL de base différente pour éviter les conflits: `/catalogue-request` et `/catalogue-session`

#### Manipulation 32: Tests manuels
- Tests avec Postman pour vérifier les différentes portées

### 9. Découplage du Contrôleur REST et de l'Implémentation du Service ✅

#### Manipulation 33: Interface de CatalogueProduits
- Créée `ICatalogueProduits` dans le package `catalogue`

#### Manipulation 34: Annotation @Service
- `CatalogueProduits` annotée avec `@Service`

#### Manipulation 35: Injection avec @Autowired
- Injection dans tous les contrôleurs:
```java
@Autowired
private CatalogueProduits catalogueProduits;
```

#### Manipulation 36: Tests manuels
- Tests avec Postman pour vérifier le découplage

### 10. Développement d'une Interface Web (Front-end) pour l'API REST (Ajax) ✅

#### Manipulation 37: Page index.html
- Page Web unique avec:
  - Bouton de consultation des produits
  - Formulaires d'ajout et de suppression
  - Zones d'affichage des résultats
  - Tableau pour afficher la liste des produits

#### Manipulation 38: Fonctions JavaScript/jQuery
- Implémentation complète dans `app.js`:
  - `consulterTousProduits()` - GET /catalogue/produits
  - `consulterProduit(numero)` - GET /catalogue/produit/{numero}
  - `ajouterProduitAvecParams()` - POST avec paramètres
  - `ajouterProduitAvecJSON()` - POST avec body JSON
  - `supprimerProduit(numero)` - DELETE /catalogue/produit/{numero}
  - Gestion des formats JSON/XML
  - Affichage dynamique dans le tableau HTML

## Fonctionnalités Supplémentaires Implémentées

### 1. Support XML
- Annotations JAXB sur la classe `Produit`:
```java
@XmlRootElement
public class Produit { ... }
```
- Parsing XML dans le JavaScript pour l'interface Web

### 2. Gestion des Erreurs
- Codes HTTP appropriés pour toutes les réponses
- Messages d'erreur clairs dans l'interface Web

### 3. Interface Utilisateur Complète
- Design moderne avec Bootstrap 5
- Styles CSS personnalisés
- Responsive design
- Expérience utilisateur intuitive

### 4. Configuration Spring Boot
- Configuration des ressources statiques
- Support CORS (si nécessaire)
- Configuration Jackson pour le format JSON

### 5. Documentation
- README.md complet avec:
  - Structure du projet
  - Endpoints de l'API
  - Exemples d'utilisation avec cURL
  - Instructions d'exécution

## Structure Complète du Projet

```
tp-spring-rest/
├── pom.xml
├── README.md
├── IMPLEMENTATION_SUMMARY.md
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── tpspringrest/
    │   │   │   └── TpSpringRestApplication.java
    │   │   ├── catalogue/
    │   │   │   ├── Produit.java
    │   │   │   ├── ICatalogueProduits.java
    │   │   │   └── CatalogueProduits.java
    │   │   └── controleurs/rest/
    │   │       ├── ConfigREST.java
    │   │       ├── CatalogueRestAPI.java
    │   │       ├── CatalogueRestAPIRequestScope.java
    │   │       ├── CatalogueRestAPISessionScope.java
    │   │       └── WebController.java
    │   └── resources/
    │       ├── application.properties
    │       └── static/
    │           ├── index.html
    │           ├── css/
    │           │   └── style.css
    │           └── js/
    │               └── app.js
    └── test/
        └── java/
```

## Comment Tester

### 1. Avec Maven
```bash
cd tp-spring-rest
mvn spring-boot:run
```

### 2. Avec Java
```bash
mvn clean package
java -jar target/tp-spring-rest-0.0.1-SNAPSHOT.jar
```

### 3. Accéder à l'Application
- **API REST**: `http://localhost:2020/api-rest/catalogue`
- **Interface Web**: `http://localhost:2020/`

### 4. Tests avec cURL
```bash
# Ajouter un produit
curl -X POST "http://localhost:2020/api-rest/catalogue/produit?pNom=Test&pCateg=Test"

# Consulter tous les produits
curl -X GET http://localhost:2020/api-rest/catalogue/produits

# Consulter un produit spécifique
curl -X GET http://localhost:2020/api-rest/catalogue/produit/1

# Supprimer un produit
curl -X DELETE http://localhost:2020/api-rest/catalogue/produit/1
```

### 5. Tests avec Postman
1. Créer une nouvelle collection
2. Ajouter les requêtes pour chaque endpoint
3. Configurer les headers:
   - `Accept`: `application/json` ou `application/xml`
   - `Content-Type`: `application/json` (pour les POST avec body)

## Conclusion

Toutes les manipulations du TP ont été implémentées avec succès :
- ✅ Création du projet Spring Boot
- ✅ Configuration de l'application
- ✅ Implémentation de l'API REST
- ✅ Négociation de formats (JSON/XML)
- ✅ Portée des contrôleurs (Singleton, RequestScope, SessionScope)
- ✅ Découplage avec injection de dépendances
- ✅ Interface Web SPA avec JavaScript/jQuery
- ✅ Tests et documentation

Le projet est prêt à être compilé, exécuté et testé.
