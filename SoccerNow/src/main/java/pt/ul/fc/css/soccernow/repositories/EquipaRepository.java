package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.soccernow.entities.equipas.IEquipa;

public interface EquipaRepository extends JpaRepository<IEquipa, Long> {
    
}
