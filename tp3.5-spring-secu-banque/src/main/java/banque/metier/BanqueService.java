package banque.metier;

import banque.dao.ClientRepository;
import banque.dao.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BanqueService {

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private CompteRepository compteRepository;

    // Gestion des clients
    public Client ajouterClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public void supprimerClient(Long id) {
        clientRepository.deleteById(id);
    }

    // Gestion des comptes
    public Compte ajouterCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    public Optional<Compte> getCompteById(Long id) {
        return compteRepository.findById(id);
    }

    public Compte getCompteByNumero(String numero) {
        return compteRepository.findByNumero(numero);
    }

    public List<Compte> getComptesByClient(Long clientId) {
        return compteRepository.findByClientId(clientId);
    }

    public void supprimerCompte(Long id) {
        compteRepository.deleteById(id);
    }

    // Opérations bancaires
    public Compte verser(String numeroCompte, double montant) {
        Compte compte = compteRepository.findByNumero(numeroCompte);
        if (compte != null) {
            compte.setSolde(compte.getSolde() + montant);
            return compteRepository.save(compte);
        }
        return null;
    }

    public Compte retirer(String numeroCompte, double montant) {
        Compte compte = compteRepository.findByNumero(numeroCompte);
        if (compte != null && compte.getSolde() >= montant) {
            compte.setSolde(compte.getSolde() - montant);
            return compteRepository.save(compte);
        }
        return null;
    }

    public Compte virement(String numeroSource, String numeroDestination, double montant) {
        Compte compteSource = compteRepository.findByNumero(numeroSource);
        Compte compteDestination = compteRepository.findByNumero(numeroDestination);
        
        if (compteSource != null && compteDestination != null && compteSource.getSolde() >= montant) {
            compteSource.setSolde(compteSource.getSolde() - montant);
            compteDestination.setSolde(compteDestination.getSolde() + montant);
            
            compteRepository.save(compteSource);
            compteRepository.save(compteDestination);
            
            return compteSource;
        }
        return null;
    }
}
