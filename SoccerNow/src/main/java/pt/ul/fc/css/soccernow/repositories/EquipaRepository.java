package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.equipas.Equipa;

@Repository
public interface EquipaRepository extends JpaRepository<Equipa, Long> {
    
}
