package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;

@Repository
public interface SelecaoRepository extends JpaRepository<Selecao, Long> {
  /*
    @Query("select case when count(s)> 0 then true else false end "+
    "from Selecao s where jogosSimultaneos.contains(s.jogoId) and"+
    "(s.guardaRedes = :utilizadorId or s.alaEsquerda = :utilizadorId or s.alaDireita = :utilizadorId or s.fixo = :utilizadorId or s.pivot = :utilizadorId)")
  boolean existsJogoAtSameIntervalContainedIn(@Param("utilizadorId") Long selecaoId, @Param("start") List<Long> jogosSimultaneos);
  */
}
