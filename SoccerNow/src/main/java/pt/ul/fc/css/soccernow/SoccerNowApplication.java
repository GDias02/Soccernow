package pt.ul.fc.css.soccernow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

@SpringBootApplication
public class SoccerNowApplication {

    @Autowired private JogadorHandler jogadorHandler;

    public static void main(String[] args) {
        SpringApplication.run(SoccerNowApplication.class, args);

    }

    @Bean
    @Transactional
    public CommandLineRunner demo() {
        return (args) -> {
            System.out.println("do some sanity tests here");

            //Add a starting user to the DB
            JogadorPostDto utilizadorInicial = 
                new JogadorPostDto(new UtilizadorDto(0L, 123456789, "Soberbo", "919191919"), Posicao.GUARDA_REDES);
            jogadorHandler.registarJogador(utilizadorInicial);
        };
    }
}
