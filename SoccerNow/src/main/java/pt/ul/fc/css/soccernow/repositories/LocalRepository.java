package pt.ul.fc.css.soccernow.repositories;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.jogos.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

  @Transactional
  Optional<Local> findByNome(@Param("nome") String nome);
}
