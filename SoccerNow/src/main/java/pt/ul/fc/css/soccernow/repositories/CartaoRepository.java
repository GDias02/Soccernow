package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.soccernow.entities.jogos.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    
}
