package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.equipas.Conquista;

@Repository
public interface ConquistaRepository extends JpaRepository<Conquista, Long> {
    
}
