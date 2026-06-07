package security.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_accounts")
public class UserAccounts implements Serializable {
    
    @Id
    @Column(name = "login", length = 50, nullable = false, unique = true)
    private String login;
    
    @Column(name = "password", length = 100, nullable = false)
    private String password;
    
    @Column(name = "roles", length = 100, nullable = false)
    private String roles;
    
    public UserAccounts() {
        super();
    }
    
    public UserAccounts(String login, String password, String roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRoles() {
        return roles;
    }
    
    public void setRoles(String roles) {
        this.roles = roles;
    }
    
    @Override
    public String toString() {
        return "UserAccounts{" + 
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
