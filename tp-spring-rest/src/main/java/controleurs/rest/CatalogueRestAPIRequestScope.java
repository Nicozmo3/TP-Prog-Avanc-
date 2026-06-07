package controleurs.rest;

import catalogue.CatalogueProduits;
import catalogue.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Contrôleur REST avec portée RequestScope
 * Chaque requête HTTP a sa propre instance du contrôleur
 */
@RestController
@RequestMapping("/catalogue-request")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CatalogueRestAPIRequestScope {

    @Autowired
    private CatalogueProduits catalogueProduits;

    /**
     * Ajoute un produit au catalogue
     */
    @PostMapping(value = "/produit", 
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Produit> ajouterProduit (
            @RequestParam("pNom") String nomProd,
            @RequestParam("pCateg") String categProd) {
        
        Produit produit = catalogueProduits.ajouterProduit(nomProd, categProd);
        return new ResponseEntity<Produit>(produit, HttpStatus.CREATED);
    }

    /**
     * Consulte un produit par son numéro
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
     */
    @GetMapping(value = "/produits", 
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Produit>> getTousProduits () {
        List<Produit> produits = catalogueProduits.getTousProduits();
        return new ResponseEntity<List<Produit>>(produits, HttpStatus.OK);
    }
}
