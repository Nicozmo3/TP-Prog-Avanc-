package init;

import banque.metier.Client;
import banque.metier.Compte;
import banque.metier.BanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitBanqueData implements CommandLineRunner {

    @Autowired
    private BanqueService banqueService;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si des clients existent déjà
        if (banqueService.getAllClients().isEmpty()) {
            // Créer des clients
            Client client1 = new Client("Dupont", "Jean", "jean.dupont@email.com", "0123456789");
            Client client2 = new Client("Martin", "Marie", "marie.martin@email.com", "0987654321");
            
            client1 = banqueService.ajouterClient(client1);
            client2 = banqueService.ajouterClient(client2);
            
            // Créer des comptes pour les clients
            Compte compte1 = new Compte("CPT001", 1000.0, client1);
            Compte compte2 = new Compte("CPT002", 5000.0, client1);
            Compte compte3 = new Compte("CPT003", 2000.0, client2);
            
            banqueService.ajouterCompte(compte1);
            banqueService.ajouterCompte(compte2);
            banqueService.ajouterCompte(compte3);
            
            System.out.println("Initialisation des données bancaires terminée");
            System.out.println("Clients créés: " + client1.getNom() + ", " + client2.getNom());
            System.out.println("Comptes créés: " + compte1.getNumero() + ", " + compte2.getNumero() + ", " + compte3.getNumero());
        }
    }
}
