package pt.ul.fc.css.soccernow.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {

  List<Jogo> findAllByDiaEHoraBetween(LocalDateTime diaEHoraStart, LocalDateTime diaEHoraEnd);

  @Query(
      "SELECT CASE WHEN COUNT(j) > 0 THEN TRUE ELSE FALSE END from Jogo j join j.local l where l.id"
          + " = :localId and j.diaEHora between :start and :end")
  boolean existsLocalAtSameIntervalContainedIn(
      @Param("localId") Long localId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);
}
