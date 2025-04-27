package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.soccernow.entities.jogos.Golo;

public interface GoloRepository extends JpaRepository<Golo, Long> {
    
}
