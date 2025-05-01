package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {
    //TODO
}
