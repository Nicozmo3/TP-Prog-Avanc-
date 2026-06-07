package banque.web;

import banque.metier.BanqueService;
import banque.metier.Client;
import banque.metier.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/banque")
public class BanqueController {

    @Autowired
    private BanqueService banqueService;

    // Endpoints pour les clients
    @PostMapping("/clients")
    public ResponseEntity<Client> ajouterClient(@RequestBody Client client) {
        Client nouveauClient = banqueService.ajouterClient(client);
        return ResponseEntity.ok(nouveauClient);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = banqueService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = banqueService.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/clients/email/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        Client client = banqueService.getClientByEmail(email);
        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> supprimerClient(@PathVariable Long id) {
        banqueService.supprimerClient(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints pour les comptes
    @PostMapping("/comptes")
    public ResponseEntity<Compte> ajouterCompte(@RequestBody Compte compte) {
        Compte nouveauCompte = banqueService.ajouterCompte(compte);
        return ResponseEntity.ok(nouveauCompte);
    }

    @GetMapping("/comptes")
    public ResponseEntity<List<Compte>> getAllComptes() {
        List<Compte> comptes = banqueService.getAllComptes();
        return ResponseEntity.ok(comptes);
    }

    @GetMapping("/comptes/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Optional<Compte> compte = banqueService.getCompteById(id);
        return compte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/comptes/numero/{numero}")
    public ResponseEntity<Compte> getCompteByNumero(@PathVariable String numero) {
        Compte compte = banqueService.getCompteByNumero(numero);
        if (compte != null) {
            return ResponseEntity.ok(compte);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/clients/{clientId}/comptes")
    public ResponseEntity<List<Compte>> getComptesByClient(@PathVariable Long clientId) {
        List<Compte> comptes = banqueService.getComptesByClient(clientId);
        return ResponseEntity.ok(comptes);
    }

    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> supprimerCompte(@PathVariable Long id) {
        banqueService.supprimerCompte(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints pour les opérations bancaires
    @PostMapping("/comptes/{numero}/verser")
    public ResponseEntity<Compte> verser(@PathVariable String numero, @RequestParam double montant) {
        Compte compte = banqueService.verser(numero, montant);
        if (compte != null) {
            return ResponseEntity.ok(compte);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/comptes/{numero}/retirer")
    public ResponseEntity<Compte> retirer(@PathVariable String numero, @RequestParam double montant) {
        Compte compte = banqueService.retirer(numero, montant);
        if (compte != null) {
            return ResponseEntity.ok(compte);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/virement")
    public ResponseEntity<Compte> virement(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam double montant) {
        Compte compte = banqueService.virement(source, destination, montant);
        if (compte != null) {
            return ResponseEntity.ok(compte);
        }
        return ResponseEntity.badRequest().build();
    }
}
