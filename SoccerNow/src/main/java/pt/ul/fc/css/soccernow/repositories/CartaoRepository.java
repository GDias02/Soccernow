package pt.ul.fc.css.soccernow.repositories;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
  Set<Cartao> findByAtribuidoA(Long atribuidoA);

  Set<Cartao> findByArbitro(Long arbitro);

  Set<Cartao> findByJogo(Long jogo);
}
