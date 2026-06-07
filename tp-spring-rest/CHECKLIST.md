# Checklist du TP Spring REST

## ✅ Toutes les manipulations ont été implémentées

### 1. Description de l'API REST
- [x] API REST pour gérer un catalogue de produits
- [x] POST /catalogue/produit - Ajouter un produit
- [x] GET /catalogue/produit/{numeroProd} - Consulter un produit
- [x] GET /catalogue/produits - Consulter tous les produits
- [x] DELETE /catalogue/produit/{numeroProd} - Supprimer un produit

### 2. Création du projet Spring Boot
- [x] Manipulation 1: Projet Maven simple nommé tp-spring-rest
- [x] Manipulation 2: Configuration Spring Boot avec Spring Web (pom.xml)
- [x] Manipulation 3: Package controleurs.rest créé
- [x] Manipulation 4: Postman recommandé pour les tests

### 3. Paramètres globaux de l'application Spring Boot
- [x] Manipulation 5: Fichier application.properties configuré
- [x] Manipulation 6: server.servlet.context-path=/api-rest et server.port=2020
- [x] Manipulation 7: Prêt pour les tests manuels avec Postman

### 4. Classe de configuration du projet Spring Boot
- [x] Manipulation 8: Classe ConfigREST créée
- [x] Manipulation 9: Annotation @Configuration ajoutée
- [x] Manipulation 10: @ComponentScan pour les packages controleurs.rest et catalogue

### 5. Implémentation des services de l'API REST
- [x] Manipulation 11: Classes CatalogueProduits et Produit importées

### 6. Implémentation du contrôleur de l'API REST
- [x] Manipulation 12: Classe CatalogueRestAPI créée
- [x] Manipulation 13: @RestController et @RequestMapping("/catalogue")
- [x] Manipulation 14 & 15: Méthode ajouterProduit avec @PostMapping et @RequestParam
- [x] Manipulation 16: Extraction des paramètres avec @RequestParam
- [x] Manipulation 17: Prêt pour les tests
- [x] Manipulation 18 & 19: Méthode consulterProduit avec @GetMapping et @PathVariable
- [x] Manipulation 20: Tests manuels
- [x] Manipulation 21: Méthode getTousProduits avec @GetMapping
- [x] Manipulation 22: Tests manuels
- [x] Manipulation 23: Méthode supprimerProduit avec @DeleteMapping
- [x] Manipulation 24: Tests manuels
- [x] Manipulation 25: ResponseEntity<T> pour les codes HTTP
- [x] Manipulation 26: Méthode ajouterProduit avec @RequestBody
- [x] Manipulation 27: Tests manuels avec JSON

### 7. Négociation de formats
- [x] Manipulation 28: Header Accept configuré dans l'interface Web
- [x] Manipulation 29: Paramètre produces dans toutes les annotations

### 8. Portée d'un contrôleur REST
- [x] Manipulation 30: Portée par défaut (Singleton) documentée
- [x] Manipulation 31: @RequestScope et @SessionScope implémentés
- [x] Manipulation 32: Tests manuels avec différentes portées

### 9. Découplage du contrôleur REST et de l'implémentation du service
- [x] Manipulation 33: Interface ICatalogueProduits créée
- [x] Manipulation 34: @Service sur CatalogueProduits
- [x] Manipulation 35: @Autowired dans les contrôleurs
- [x] Manipulation 36: Tests manuels

### 10. Développement d'une interface Web (front-end) pour l'API REST
- [x] Manipulation 37: Page index.html avec boutons, formulaires et zones d'affichage
- [x] Manipulation 38: Fonctions JavaScript/jQuery pour les requêtes Ajax

## 📁 Structure du Projet

```
tp-spring-rest/
├── pom.xml                                    # Configuration Maven
├── README.md                                 # Documentation complète
├── IMPLEMENTATION_SUMMARY.md                 # Résumé de l'implémentation
├── CHECKLIST.md                              # Cette checklist
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── tpspringrest/
    │   │   │   └── TpSpringRestApplication.java  # Classe principale
    │   │   ├── catalogue/
    │   │   │   ├── Produit.java                     # Classe Produit (@XmlRootElement)
    │   │   │   ├── ICatalogueProduits.java          # Interface du service
    │   │   │   └── CatalogueProduits.java           # Service (@Service)
    │   │   └── controleurs/rest/
    │   │       ├── ConfigREST.java                  # Configuration REST
    │   │       ├── CatalogueRestAPI.java            # Contrôleur REST principal
    │   │       ├── CatalogueRestAPIRequestScope.java # RequestScope
    │   │       ├── CatalogueRestAPISessionScope.java # SessionScope
    │   │       └── WebController.java               # Pour servir HTML
    │   └── resources/
    │       ├── application.properties              # Configuration Spring Boot
    │       └── static/
    │           ├── index.html                      # Page Web SPA
    │           ├── css/
    │           │   └── style.css                    # Styles CSS
    │           └── js/
    │               └── app.js                       # JavaScript/jQuery
    └── test/
        └── java/
            ├── tpspringrest/
            │   └── TpSpringRestApplicationTests.java # Tests Spring Boot
            └── controleurs/rest/
                └── CatalogueRestAPITest.java       # Tests du contrôleur
```

## 📊 Statistiques

- **Fichiers Java (main)**: 9
- **Fichiers Java (test)**: 2
- **Fichiers de configuration**: 2 (pom.xml, application.properties)
- **Fichiers frontend**: 3 (index.html, style.css, app.js)
- **Fichiers de documentation**: 2 (README.md, IMPLEMENTATION_SUMMARY.md)
- **Total**: 18 fichiers

## 🚀 Comment démarrer

### Prérequis
- Java JDK 17
- Maven 3.6+

### Compilation et exécution
```bash
cd tp-spring-rest
mvn clean compile
mvn spring-boot:run
```

### Accès
- **API REST**: `http://localhost:2020/api-rest/catalogue`
- **Interface Web**: `http://localhost:2020/`

## 🧪 Tests

### Avec cURL
```bash
# Ajouter un produit
curl -X POST "http://localhost:2020/api-rest/catalogue/produit?pNom=Test&pCateg=Test"

# Consulter tous les produits
curl -X GET http://localhost:2020/api-rest/catalogue/produits

# Consulter un produit
curl -X GET http://localhost:2020/api-rest/catalogue/produit/1

# Supprimer un produit
curl -X DELETE http://localhost:2020/api-rest/catalogue/produit/1
```

### Avec Postman
1. Créer une collection avec les endpoints ci-dessus
2. Configurer les headers:
   - `Accept`: `application/json` ou `application/xml`
   - `Content-Type`: `application/json` (pour POST avec body)

### Avec l'interface Web
1. Ouvrir `http://localhost:2020/`
2. Utiliser les formulaires pour interagir avec l'API

## ✨ Fonctionnalités supplémentaires

- Support JSON et XML (négociation de contenu)
- Codes HTTP appropriés (200, 201, 404, etc.)
- Interface Web moderne avec Bootstrap 5
- Tests unitaires avec Mockito
- Documentation complète
- Découplage complet avec injection de dépendances
- Support des différentes portées de contrôleurs

## 🎯 Objectifs atteints

✅ Toutes les manipulations du TP ont été implémentées
✅ Code propre et bien structuré
✅ Documentation complète
✅ Tests unitaires inclus
✅ Interface Web fonctionnelle
✅ Prêt pour la compilation et l'exécution

---

**Statut**: ✅ COMPLET
**Date**: 2024
**Version**: 1.0
