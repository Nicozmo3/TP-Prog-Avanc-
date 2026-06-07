package security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.entities.UserAccounts;

@Repository
public interface IUserAccountsRepository extends JpaRepository<UserAccounts, String> {
    
    UserAccounts findByLogin(String login);
}
