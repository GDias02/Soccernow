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
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    Optional<Jogador> findById(@Param("id") long id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    Optional<Jogador> findByNif(@Param("nif") int nif);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    List<Jogador> findAll();

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    @Query("SELECT j FROM Jogador j WHERE j.deleted = false AND j.id = :id")
    Optional<Jogador> findAliveById(@Param("id") long id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    @Query("SELECT j FROM Jogador j WHERE j.deleted = false AND j.nif = :nif")
    Optional<Jogador> findAliveByNif(@Param("nif") int nif);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional
    @Query("SELECT j FROM Jogador j WHERE j.deleted = false")
    List<Jogador> findAliveAll();

    @Transactional
    void deleteByNif(@Param("nif") int nif);
}
