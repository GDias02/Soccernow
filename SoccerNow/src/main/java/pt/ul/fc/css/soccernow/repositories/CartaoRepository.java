package pt.ul.fc.css.soccernow.repositories;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
  Optional<Set<Cartao>> findByAtribuidoA_Id(Long atribuidoA);

  Optional<Set<Cartao>> findByArbitro_Id(Long arbitro);

  Optional<Set<Cartao>> findByJogo_Id(Long jogo);
}
