package pt.ul.fc.css.soccernow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;

public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {
    Optional<Arbitro> findByNif(int nif);
    Optional<Arbitro> findByNifAndDelete(int nif);
}
