package pt.ul.fc.css.soccernow.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;

@Repository
public interface SelecaoRepository extends JpaRepository<Selecao, Long> {

  @Query(
      "SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END"
          + " FROM Selecao s"
          + " JOIN s.jogo j"
          + " WHERE j.diaEHora BETWEEN :start AND :end "
          + " AND (s.pivot.id IN :jogadores"
          + " OR s.fixo.id IN :jogadores"
          + " OR s.alaEsquerda.id IN :jogadores"
          + " OR s.alaDireita.id IN :jogadores"
          + " OR s.guardaRedes.id IN :jogadores)")
  boolean existsJogoAtSameIntervalContainedIn(
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end,
      @Param("jogadores") List<Long> jogadores);
}
