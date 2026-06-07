package catalogue;

import java.util.List;

/**
 * Interface pour la gestion du catalogue de produits
 */
public interface ICatalogueProduits {
    
    /**
     * Ajoute un produit au catalogue
     * @param nom nom du produit
     * @param categorie catégorie du produit
     * @return le produit ajouté
     */
    Produit ajouterProduit(String nom, String categorie);
    
    /**
     * Ajoute un produit au catalogue
     * @param produit le produit à ajouter
     * @return le produit ajouté
     */
    Produit ajouterProduit(Produit produit);
    
    /**
     * Récupère tous les produits du catalogue
     * @return la liste de tous les produits
     */
    List<Produit> getTousProduits();
    
    /**
     * Recherche un produit par son numéro
     * @param numero le numéro du produit
     * @return le produit trouvé ou null
     */
    Produit rechercherProduit(int numero);
    
    /**
     * Supprime un produit du catalogue
     * @param numero le numéro du produit à supprimer
     * @return true si la suppression a réussi
     */
    boolean suprimerProduit(int numero);
}
