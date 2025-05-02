package pt.ul.fc.css.soccernow.repositories;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;

@Repository
public interface GoloRepository extends JpaRepository<Golo, Long> {
  Set<Golo> findByMarcador(Long marcador);

  Set<Golo> findByEquipa(Long equipa);

  Set<Golo> findByJogo(Long jogo);
}
