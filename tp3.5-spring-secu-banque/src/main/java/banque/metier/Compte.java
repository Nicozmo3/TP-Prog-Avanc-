package banque.metier;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comptes")
public class Compte implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero", length = 50, nullable = false, unique = true)
    private String numero;
    
    @Column(name = "solde", nullable = false)
    private double solde;
    
    @Column(name = "date_creation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    public Compte() {
        super();
        this.dateCreation = new Date();
    }
    
    public Compte(String numero, double solde, Client client) {
        this.numero = numero;
        this.solde = solde;
        this.client = client;
        this.dateCreation = new Date();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public double getSolde() {
        return solde;
    }
    
    public void setSolde(double solde) {
        this.solde = solde;
    }
    
    public Date getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    @Override
    public String toString() {
        return "Compte{" + 
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", solde=" + solde +
                ", dateCreation=" + dateCreation +
                ", client=" + client +
                '}';
    }
}
