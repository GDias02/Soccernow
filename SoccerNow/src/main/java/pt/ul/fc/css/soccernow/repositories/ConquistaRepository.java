package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.soccernow.entities.equipas.IConquista;

public interface ConquistaRepository extends JpaRepository<IConquista, Long> {
    
}
