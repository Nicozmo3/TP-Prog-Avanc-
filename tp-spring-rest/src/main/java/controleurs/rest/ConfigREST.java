package controleurs.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuration du projet Spring Boot
 * Indique à Spring le package des classes de l'API REST
 */
@Configuration
@ComponentScan(basePackages = {"controleurs.rest", "catalogue"})
public class ConfigREST {
    // Configuration supplémentaire peut être ajoutée ici
}
