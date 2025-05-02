package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "equipa", column = @Column(name = "s1_equipa")),
    @AttributeOverride(name = "capitao", column = @Column(name = "s1_capitao")),
    @AttributeOverride(name = "guarda_redes", column = @Column(name = "s1_guarda_redes")),
    @AttributeOverride(name = "fixo", column = @Column(name = "s1_fixo")),
    @AttributeOverride(name = "ala_esquerda", column = @Column(name = "s1_ala_esquerda")),
    @AttributeOverride(name = "ala_direita", column = @Column(name = "s1_ala_direita")),
    @AttributeOverride(name = "pivot", column = @Column(name = "s1_pivot"))
  })
  private Selecao s1;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "equipa", column = @Column(name = "s2_equipa")),
    @AttributeOverride(name = "capitao", column = @Column(name = "s2_capitao")),
    @AttributeOverride(name = "guarda_redes", column = @Column(name = "s2_guarda_redes")),
    @AttributeOverride(name = "fixo", column = @Column(name = "s2_fixo")),
    @AttributeOverride(name = "ala_esquerda", column = @Column(name = "s2_ala_esquerda")),
    @AttributeOverride(name = "ala_direita", column = @Column(name = "s2_ala_direita")),
    @AttributeOverride(name = "pivot", column = @Column(name = "s2_pivot"))
  })
  private Selecao s2;

  /*@OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "equipa_id")
  private Equipa equipa;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "capitao_id")
  private Jogador capitao;

  @OneToOne       //de modo a evitar criar uma nova tabela que teria um tamanho fixo (uma vez que as posicoes sao sempre so 5)
  @JoinColumn(name="guarda_redes")
  private Jogador guardaRedes;
  @OneToOne
  @JoinColumn(name = "fixo")
  private Jogador fixo;
  @OneToOne
  @JoinColumn(name = "ala_esquerda")
  private Jogador alaEsquerda;
  @OneToOne
  @JoinColumn(name = "ala_direita")
  private Jogador alaDireita;
  @OneToOne
  @JoinColumn(name = "pivot")
  private Jogador pivot;
  */

  @ManyToMany private List<Arbitro> equipaDeArbitros;

  @Column(nullable = false)
  private EstadoDeJogo estadoDeJogo;

  @ManyToOne
  @JoinColumn(name = "local_id")
  private Local local;

  @Column(name = "quando", columnDefinition = "TIMESTAMP")
  private LocalDateTime diaEHora;

  @Column
  private String
      resultadoFinal; // para acesso mais rapido ao resultado de um jogo, sem necessidade de

  // carregar todas as suas estatisticas

  @Transient private Placar placar;

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

  public Selecao getS2() {
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

  public String getResultadoFinal() {
    return resultadoFinal;
  }

  public EstatisticaJogo getStats() {
    return this.stats;
  }

  public void setS1(Selecao s1) {
    this.s1 = s1;
  }

  public void setS2(Selecao s2) {
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

  public void updatePlacar() {
    this.placar = this.stats.getPlacar();
  }
}
