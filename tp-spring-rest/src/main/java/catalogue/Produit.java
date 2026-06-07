package catalogue;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * @author ouziri
 * @version 0.1
 *
 */

@XmlRootElement
public class Produit {
	private int numero;
	private String nom;
	private String categorie;
	private static int numeroAuto = 1; 
	
	public Produit() {	
	}

	public Produit(String nom, String categorie) {		
		this.nom = nom;
		this.categorie = categorie;
	}

	public static int nextSequence() {
		return numeroAuto ++;
	}
	
	public int getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		try {
			Produit pObj = (Produit) obj;
			return this.numero == pObj.getNumero();
		}
		catch (Exception e) {
			return false;
		}
	}
}