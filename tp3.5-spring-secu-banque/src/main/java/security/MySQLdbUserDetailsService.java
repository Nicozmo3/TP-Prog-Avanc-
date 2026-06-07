package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.entities.UserAccounts;
import security.repository.IUserAccountsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MySQLdbUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserAccountsRepository userAccountsRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserAccounts userAccount = userAccountsRepository.findByLogin(login);
        
        if (userAccount == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le login: " + login);
        }
        
        // Convertir les rôles en GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] roles = userAccount.getRoles().split(",");
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.trim()));
        }
        
        return new User(
            userAccount.getLogin(),
            userAccount.getPassword(),
            authorities
        );
    }
}
