package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.jogos.Golo;

@Repository
public interface GoloRepository extends JpaRepository<Golo, Long> {
    
}
