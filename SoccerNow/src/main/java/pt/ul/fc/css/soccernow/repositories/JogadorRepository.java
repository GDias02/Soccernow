package pt.ul.fc.css.soccernow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    Optional<Jogador> findByNif(int nif);
    Optional<Jogador> findByNifAndDelete(int nif);
}
