package controleurs.rest;

import catalogue.CatalogueProduits;
import catalogue.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion du catalogue de produits
 * URL de base : /catalogue
 */
@RestController
@RequestMapping("/catalogue")
public class CatalogueRestAPI {

    @Autowired
    private CatalogueProduits catalogueProduits;

    /**
     * Ajoute un produit au catalogue
     * URL : POST /catalogue/produit
     * Paramètres : pNom (nom du produit), pCateg (catégorie du produit)
     * @param nomProd nom du produit
     * @param categProd catégorie du produit
     * @return le produit ajouté
     */
    @PostMapping(value = "/produit", 
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 params = {"pNom", "pCateg"})
    public ResponseEntity<Produit> ajouterProduitAvecParams (
            @RequestParam("pNom") String nomProd,
            @RequestParam("pCateg") String categProd) {
        
        Produit produit = catalogueProduits.ajouterProduit(nomProd, categProd);
        return new ResponseEntity<Produit>(produit, HttpStatus.CREATED);
    }

    /**
     * Ajoute un produit au catalogue (version avec RequestBody)
     * URL : POST /catalogue/produit
     * @param produit le produit à ajouter (au format JSON)
     * @return le produit ajouté
     */
    @PostMapping(value = "/produit", 
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Produit> ajouterProduitAvecBody (@RequestBody Produit produit) {
        Produit p = catalogueProduits.ajouterProduit(produit);
        return new ResponseEntity<Produit>(p, HttpStatus.CREATED);
    }

    /**
     * Consulte un produit par son numéro
     * URL : GET /catalogue/produit/{numeroProd}
     * @param numeroProd le numéro du produit
     * @return le produit trouvé ou 404 si non trouvé
     */
    @GetMapping(value = "/produit/{numeroProd}", 
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Produit> consulterProduit (@PathVariable("numeroProd") int numeroProd) {
        Produit produit = catalogueProduits.rechercherProduit(numeroProd);
        if (produit != null) {
            return new ResponseEntity<Produit>(produit, HttpStatus.OK);
        } else {
            return new ResponseEntity<Produit>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Consulte tous les produits du catalogue
     * URL : GET /catalogue/produits
     * @return la liste de tous les produits
     */
    @GetMapping(value = "/produits", 
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Produit>> getTousProduits () {
        List<Produit> produits = catalogueProduits.getTousProduits();
        return new ResponseEntity<List<Produit>>(produits, HttpStatus.OK);
    }

    /**
     * Supprime un produit du catalogue
     * URL : DELETE /catalogue/produit/{numeroProd}
     * @param numeroProd le numéro du produit à supprimer
     * @return code HTTP 200 si succès, 404 si produit non trouvé
     */
    @DeleteMapping(value = "/produit/{numeroProd}", 
                   produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Integer> supprimerProduit (@PathVariable("numeroProd") int numeroProd) {
        try {
            boolean result = catalogueProduits.suprimerProduit(numeroProd);
            if (result) {
                return new ResponseEntity<Integer>(numeroProd, HttpStatus.OK);
            } else {
                return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
        }
    }
}
