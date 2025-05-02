package pt.ul.fc.css.soccernow.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    Optional<Arbitro> findById(int id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    Optional<Arbitro> findByNif(int nif);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    List<Arbitro> findAll();

    @Transactional
    void deleteByNif(int nif);
}
