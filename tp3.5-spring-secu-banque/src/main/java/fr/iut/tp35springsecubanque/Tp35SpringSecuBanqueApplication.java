package fr.iut.tp35springsecubanque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"api.rest", "security", "init", "banque"})
@EntityScan(basePackages = {"security.entities", "banque.metier"})
@EnableJpaRepositories(basePackages = {"security.repository", "banque.dao"})
public class Tp35SpringSecuBanqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp35SpringSecuBanqueApplication.class, args);
    }
}
