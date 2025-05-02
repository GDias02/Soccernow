package pt.ul.fc.css.soccernow.repositories;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;

@Repository
public interface GoloRepository extends JpaRepository<Golo, Long> {
  Optional<Set<Golo>> findByMarcador_Id(Long marcador);

  Optional<Set<Golo>> findByEquipa_Id(Long equipa);

  Optional<Set<Golo>> findByJogo_Id(Long jogo);
}
