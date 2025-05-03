package pt.ul.fc.css.soccernow.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.jogos.GoloDto;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.CertificadoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.mappers.jogos.CartaoMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.GoloMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorPostMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.CartaoRepository;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.GoloRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;
import pt.ul.fc.css.soccernow.repositories.LocalRepository;

@Service
public class InitHandler {

    @Autowired
    private JogadorHandler jogadorHandler;

    @Autowired
    private ArbitroHandler arbitroHandler;

    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private JogoHandler jogoHandler;

    @Autowired
    private EstatisticasHandler estatisticasHandler;

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private ArbitroRepository arbitroRepository;

    @Autowired
    private EquipaRepository equipaRepository;

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private GoloRepository goloRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private LocalRepository localRepository;

    @Transactional
    public void init() {
        deleteAll();
    }

    private void deleteAll() {
        localRepository.deleteAll();
        goloRepository.deleteAll();
        cartaoRepository.deleteAll();
        jogoRepository.deleteAll();
        equipaRepository.deleteAll();
        arbitroRepository.deleteAll();
        jogadorRepository.deleteAll();
    }

    private void fillJogos() {
        LocalDto l1 = new LocalDto(null, "Estadio1", "Prop1", 10, new MoradaDto("1111-111", "Rua 1", "Localidade1", "Cidade1", "Estado1", "Pais1"));
        LocalDto l2 = new LocalDto(null, "Estadio2", "Prop2", 20, new MoradaDto("2222-222", "Rua 2", "Localidade2", "Cidade2", "Estado2", "Pais2"));
        LocalDto l3 = new LocalDto(null, "Estadio3", "Prop3", 30, new MoradaDto("3333-333", "Rua 3", "Localidade3", "Cidade3", "Estado3", "Pais3"));

        LocalDateTime d1 = LocalDateTime.parse("2025-01-01T01:01:01");
        LocalDateTime d2 = LocalDateTime.parse("2025-02-02T02:02:02");
        LocalDateTime d3 = LocalDateTime.parse("2025-03-03T03:03:03");

        UtilizadorDto u0 = new UtilizadorDto(null, 111111110, "007", "900000000");
        UtilizadorDto u1 = new UtilizadorDto(null, 111111111, "Ana", "911111111");
        UtilizadorDto u2 = new UtilizadorDto(null, 122222222, "Bernardo", "922222222");
        UtilizadorDto u3 = new UtilizadorDto(null, 133333333, "Carla", "933333333");
        UtilizadorDto u4 = new UtilizadorDto(null, 144444444, "Daniel", "944444444");
        UtilizadorDto u5 = new UtilizadorDto(null, 155555555, "Ermelinda", "955555555");
        UtilizadorDto u6 = new UtilizadorDto(null, 166666666, "Fernando", "966666666");
        UtilizadorDto u7 = new UtilizadorDto(null, 177777777, "Gertrudes", "977777777");
        UtilizadorDto u8 = new UtilizadorDto(null, 188888888, "Helder", "988888888");
        UtilizadorDto u9 = new UtilizadorDto(null, 199999999, "Idalina", "999999999");
        UtilizadorDto u10 = new UtilizadorDto(null, 101010101, "Jorge", "901010101");
        UtilizadorDto u11 = new UtilizadorDto(null, 202020202, "Leonor", "902020202");
        UtilizadorDto u12 = new UtilizadorDto(null, 303030303, "Miguel", "903030303");      
        ArbitroDto a1 = new ArbitroDto(u10, null);
        ArbitroDto a2 = new ArbitroDto(u11, null);
        ArbitroDto a3 = new ArbitroDto(u12, new CertificadoDto(true));
          
        JogadorDto j11 = new JogadorDto(u0, Posicao.GUARDA_REDES, null);
        JogadorDto j12 = new JogadorDto(u1, Posicao.FIXO, null);
        JogadorDto j13 = new JogadorDto(u2, Posicao.ALA_ESQUERDA, null);
        JogadorDto j14 = new JogadorDto(u3, Posicao.ALA_DIREITA, null);
        JogadorDto j15 = new JogadorDto(u4, Posicao.PIVO, null);
        JogadorDto j21 = new JogadorDto(u5, Posicao.GUARDA_REDES, null);
        JogadorDto j22 = new JogadorDto(u6, Posicao.FIXO, null);
        JogadorDto j23 = new JogadorDto(u7, Posicao.ALA_ESQUERDA, null);
        JogadorDto j24 = new JogadorDto(u8, Posicao.ALA_DIREITA, null);
        JogadorDto j25 = new JogadorDto(u9, Posicao.PIVO, null);

        Long j1id = 1L;
        Long j2id = 2L;
        Long j3id = 3L;
        Long j4id = 4L;
        Long j5id = 5L;
        Long j6id = 6L;
        Long j7id = 7L;
        Long j8id = 8L;
        Long j9id = 9L;
        Long j10id = 10L;
        
        Long a1id = 11L;
        Long a2id = 12L;
        Long a3id = 13L;
        
        List<Long> lista1 = new ArrayList<>();
        lista1.add(j1id);
        lista1.add(j2id);
        lista1.add(j3id);
        lista1.add(j4id);
        lista1.add(j5id);
        List<Long> lista2 = new ArrayList<>();
        lista2.add(j6id);
        lista2.add(j7id);
        lista2.add(j8id);
        lista2.add(j9id);
        lista2.add(j10id);
        List<Long> lista3 = new ArrayList<>();
        lista3.add(j1id);
        lista3.add(j2id);
        lista3.add(j3id);

        EquipaDto e1 = new EquipaDto(null, "Abelhudos", lista1);
        EquipaDto e2 = new EquipaDto(null, "Bananas", lista2);
        EquipaDto e3 = new EquipaDto(null, "Coscuvilheiros", lista3);    
          
        Map<Posicao, Long> s1j = new EnumMap<>(Posicao.class);
        for(int i= 0; i<6; i++){
            s1j.put(Posicao.values()[i], lista1.get(i));
        }
        Map<Posicao, Long> s2j = new EnumMap<>(Posicao.class);
        for(int i= 0; i<6; i++){
            s2j.put(Posicao.values()[i], lista2.get(i));
        }

        SelecaoDto s1 = new SelecaoDto();
        SelecaoDto s2 = new SelecaoDto();
        s1.setJogadores(s1j);
        s1.setJogadores(s2j);  

        List<ArbitroDto> arbitros = new ArrayList<>();
        arbitros.add(a1);
        arbitros.add(a2);
        arbitros.add(a3);   

        JogoDto j1 = new JogoDto(null, EstadoDeJogo.AGENDADO, l1, d1, s1, s2, arbitros);
    }
}
