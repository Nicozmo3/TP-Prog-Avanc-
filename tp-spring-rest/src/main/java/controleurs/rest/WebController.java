package controleurs.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur pour servir les pages HTML statiques
 */
@Controller
public class WebController {

    /**
     * Redirige vers la page d'accueil
     * @return le nom de la page HTML
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }
    
    /**
     * Sert la page index.html
     * @return le chemin vers la page HTML
     */
    @GetMapping("/index.html")
    public String serveIndex() {
        return "forward:/index.html";
    }
}
