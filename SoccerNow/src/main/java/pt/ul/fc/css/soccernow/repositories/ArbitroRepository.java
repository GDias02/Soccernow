package pt.ul.fc.css.soccernow.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    Optional<Arbitro> findById(@Param("id") long id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    Optional<Arbitro> findByNif(@Param("nif") int nif);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    List<Arbitro> findAll();

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    @Query("SELECT a FROM Arbitro a WHERE a.deleted = false AND a.id = :id")
    Optional<Arbitro> findAliveById(@Param("id") long id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    @Query("SELECT a FROM Arbitro a WHERE a.deleted = false AND a.nif = :nif")
    Optional<Arbitro> findAliveByNif(@Param("nif") int nif);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    @Query("SELECT a FROM Arbitro a WHERE a.deleted = false")
    List<Arbitro> findAliveAll();

    @Transactional
    void deleteByNif(@Param("nif") int nif);
}
