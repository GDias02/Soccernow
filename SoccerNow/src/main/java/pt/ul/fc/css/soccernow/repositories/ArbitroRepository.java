package pt.ul.fc.css.soccernow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {
    Optional<Arbitro> findByNif(int nif);

    @Transactional
    void deleteByNif(int nif);
}
