package pt.ul.fc.css.soccernow.dto.jogos;

import java.time.LocalDateTime;
import java.util.List;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;

/**
 * Classe Dto para representar um local usado para jogos. Para: - Criar um novo jogo: - - O atributo
 * id nao deve estar preenchido. - - Os atributos s1 e s2 devem estar preenchidos. - - O atributo
 * equipaDeArbitros deve ter pelo menos 1 elemento, no entanto, o ArbitroDto requer apenas o id. - -
 * O atributo local e diaEHora devem estar preenchidos - Criar um novo jogo (retorno): - - O
 * atributo id tem de estar preenchido. - Registar o resultado de um jogo: - - O atributo id tem de
 * estar preenchido. - - O atributo estadoAtual deve estar preenchido e confirmar
 * EstadoDeJogo.TERMINADO - - O atributo EstatisticaJogoDto deve estar preenchido.
 */
public class JogoDto {

  private Long id;
  private EstadoDeJogo estadoDeJogo;
  private LocalDto localDto;
  private LocalDateTime diaEHora;
  private SelecaoDto s1;
  private SelecaoDto s2;
  private List<ArbitroDto> equipaDeArbitros;
  private EstatisticaJogoDto stats;

  public JogoDto() {}

  public JogoDto(
      Long id,
      EstadoDeJogo estadoDeJogo,
      LocalDto localDto,
      LocalDateTime diaEHora,
      SelecaoDto s1,
      SelecaoDto s2,
      List<ArbitroDto> equipaDeArbitros) {
    this.id = id;
    this.estadoDeJogo = estadoDeJogo;
    this.localDto = localDto;
    this.diaEHora = diaEHora;
    this.s1 = s1;
    this.s2 = s2;
    this.equipaDeArbitros = equipaDeArbitros;
  }

  public EstatisticaJogoDto getStats() {
    return this.stats;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public EstadoDeJogo getEstadoDeJogo() {
    return estadoDeJogo;
  }

  public void setEstadoDeJogo(EstadoDeJogo estadoDeJogo) {
    this.estadoDeJogo = estadoDeJogo;
  }

  public LocalDto getLocalDto() {
    return localDto;
  }

  public void setLocalDto(LocalDto localDto) {
    this.localDto = localDto;
  }

  public SelecaoDto getS1() {
    return s1;
  }

  public void setS1(SelecaoDto s1) {
    this.s1 = s1;
  }

  public SelecaoDto getS2() {
    return s2;
  }

  public void setS2(SelecaoDto s2) {
    this.s2 = s2;
  }

  public List<ArbitroDto> getEquipaDeArbitros() {
    return equipaDeArbitros;
  }

  public void setEquipaDeArbitros(List<ArbitroDto> equipaDeArbitros) {
    this.equipaDeArbitros = equipaDeArbitros;
  }

  public LocalDateTime getDiaEHora() {
    return diaEHora;
  }

  public void setDiaEHora(LocalDateTime diaEHora) {
    this.diaEHora = diaEHora;
  }

  public void setStats(EstatisticaJogoDto stats) {
    this.stats = stats;
  }

  @Override
  public String toString() {
    return "JogoDto [id="
        + id
        + ", estadoDeJogo="
        + estadoDeJogo
        + ", localDto="
        + localDto
        + ", diaEHora="
        + diaEHora
        + ", s1="
        + s1
        + ", s2="
        + s2
        + ", equipaDeArbitros="
        + equipaDeArbitros
        + ", stats="
        + stats
        + "]";
  }
}
