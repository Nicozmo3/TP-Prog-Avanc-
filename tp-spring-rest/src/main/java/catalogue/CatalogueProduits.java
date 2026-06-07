package catalogue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author ouziri
 * @version 0.1
 *
 */

@Service
public class CatalogueProduits implements ICatalogueProduits {

	private List<Produit> produits = new ArrayList<Produit>();

	public Produit ajouterProduit (String nom, String categorie) {
		Produit p = new Produit(nom, categorie);
		p.setNumero(Produit.nextSequence());
		this.produits.add(p);
		return p;
	}

	public Produit ajouterProduit (Produit produit) {
		Produit p = this.ajouterProduit(produit.getNom(), produit.getCategorie());
		this.produits.add(p);
		return p;
	}

	public List<Produit> getTousProduits (){         
		return this.produits;
	}

	public Produit rechercherProduit (int numero){    	
		Produit p = new Produit();
		p.setNumero(numero);
		try {
			return this.produits.get(produits.indexOf(p));	
		} catch (Exception e) {
			return null;
		}    	
	}

	public boolean suprimerProduit (int numero){    
		Produit p = this.rechercherProduit(numero);
		if (p == null)
			throw new IllegalArgumentException();
		this.produits.remove(p);
		return true;        
	}   
}