package pt.ul.fc.css.soccernow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    Optional<Jogador> findByNif(int nif);
    Optional<Jogador> deleteByNif(int nif);
}
