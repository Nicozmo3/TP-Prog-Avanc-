package banque.dao;

import banque.metier.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    
    Compte findByNumero(String numero);
    List<Compte> findByClientId(Long clientId);
    List<Compte> findBySoldeGreaterThan(double solde);
}
