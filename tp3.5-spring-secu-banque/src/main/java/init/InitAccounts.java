package init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import security.entities.UserAccounts;
import security.repository.IUserAccountsRepository;

@Component
public class InitAccounts implements CommandLineRunner {

    @Autowired
    private IUserAccountsRepository userAccountsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si les utilisateurs existent déjà
        if (userAccountsRepository.findByLogin("toto") == null) {
            String password = passwordEncoder.encode("toto123");
            UserAccounts user = new UserAccounts("toto", password, "USER");
            userAccountsRepository.save(user);
            System.out.println("Utilisateur 'toto' créé avec mot de passe chiffré");
        }
        
        if (userAccountsRepository.findByLogin("tintin") == null) {
            String password = passwordEncoder.encode("tintin123");
            UserAccounts admin = new UserAccounts("tintin", password, "ADMIN");
            userAccountsRepository.save(admin);
            System.out.println("Utilisateur 'tintin' créé avec mot de passe chiffré");
        }
        
        System.out.println("Initialisation des comptes utilisateurs terminée");
    }
}
