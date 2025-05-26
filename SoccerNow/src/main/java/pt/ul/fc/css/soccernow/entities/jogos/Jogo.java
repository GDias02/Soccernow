package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_de_jogo")
public abstract class Jogo implements IJogo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Embedded private Selecao s1;

  @Embedded private SelecaoDois s2;

  @OneToMany private List<Arbitro> equipaDeArbitros;

  @Column(nullable = false)
  private EstadoDeJogo estadoDeJogo;

  @ManyToOne
  @JoinColumn(name = "local_id")
  private Local local;

  @Column(name = "quando", columnDefinition = "TIMESTAMP")
  private LocalDateTime diaEHora;

  @Column
  private Placar placar; // para acesso mais rapido ao resultado de um jogo, sem necessidade de

  // carregar todas as suas estatisticas

  @Transient private EstatisticaJogo stats;

  Jogo() {}

  public Equipa getEquipa1() {
    return s1.getEquipa();
  }

  public Equipa getEquipa2() {
    return s2.getEquipa();
  }

  public Map<Posicao, Jogador> getSelecao(Equipa equipa) {
    if (equipa.equals(s1.getEquipa())) {
      return s1.getJogadores();
    }
    if (equipa.equals(s2.getEquipa())) {
      return s2.getJogadores();
    } else {
      return null;
    }
  }

  public Arbitro getArbitroPrincipal() {
    return equipaDeArbitros.get(0);
  }

  // Getters & Setters
  public Long getId() {
    return id;
  }

  public Placar getPlacar() {
    return this.placar;
  }

  public Selecao getS1() {
    return s1;
  }

  public SelecaoDois getS2() {
    return s2;
  }

  public List<Arbitro> getEquipaDeArbitros() {
    return equipaDeArbitros;
  }

  public Local getLocal() {
    return local;
  }

  public LocalDateTime getDiaEHora() {
    return diaEHora;
  }

  public EstadoDeJogo getEstadoDeJogo() {
    return estadoDeJogo;
  }

  public EstatisticaJogo getStats() {
    return this.stats;
  }

  public void setS1(Selecao s1) {
    this.s1 = s1;
  }

  public void setS2(SelecaoDois s2) {
    this.s2 = s2;
  }

  public void setEquipaDeArbitros(List<Arbitro> equipaDeArbitros) {
    this.equipaDeArbitros = equipaDeArbitros;
  }

  public void setLocal(Local local) {
    this.local = local;
  }

  public void setDiaEHora(LocalDateTime diaEHora) {
    this.diaEHora = diaEHora;
  }

  public void setEstadoAtual(EstadoDeJogo estadoAtual) {
    this.estadoDeJogo = estadoAtual;
  }

  public void setStats(EstatisticaJogo stats) {
    this.stats = stats;
  }

  public void setPlacar(Placar p) {
    this.placar = p;
  }

  public Equipa getEquipaVencedora() {
    Equipa e = null;
    if (this.estadoDeJogo == EstadoDeJogo.TERMINADO) {
      int score1 = this.placar.getPontuacao1();
      int score2 = this.placar.getPontuacao2();
      if (score1 > score2) {
        e = s1.getEquipa();
      } else if (score2 > score1) {
        e = s2.getEquipa();
      }
    }
    return e;
  }

  @Override
  public String toString() {
    return "Jogo [id="
        + id
        + ", s1="
        + s1
        + ", s2="
        + s2
        + ", equipaDeArbitros="
        + equipaDeArbitros
        + ", estadoDeJogo="
        + estadoDeJogo
        + ", local="
        + local
        + ", diaEHora="
        + diaEHora
        + ", placar="
        + placar
        + ", stats="
        + stats
        + "]";
  }
}
