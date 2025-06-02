package pt.ul.fc.css.soccernow.dto.jogos;

import java.time.LocalDateTime;
import java.util.List;

import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
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
  private List<ArbitroPostDto> equipaDeArbitros;
  private EstatisticaJogoDto stats;
  private Long equipaVencedora;
  private Long campeonato;

  public JogoDto() {}

  public JogoDto(
      Long id,
      EstadoDeJogo estadoDeJogo,
      LocalDto localDto,
      LocalDateTime diaEHora,
      SelecaoDto s1,
      SelecaoDto s2,
      List<ArbitroPostDto> equipaDeArbitros) {
    this.id = id;
    this.estadoDeJogo = estadoDeJogo;
    this.localDto = localDto;
    this.diaEHora = diaEHora;
    this.s1 = s1;
    this.s2 = s2;
    this.equipaDeArbitros = equipaDeArbitros;
  }

  public JogoDto(
      Long id,
      EstadoDeJogo estadoDeJogo,
      LocalDto localDto,
      LocalDateTime diaEHora,
      SelecaoDto s1,
      SelecaoDto s2,
      List<ArbitroPostDto> equipaDeArbitros,
      Long campeonato) {
    this.id = id;
    this.estadoDeJogo = estadoDeJogo;
    this.localDto = localDto;
    this.diaEHora = diaEHora;
    this.s1 = s1;
    this.s2 = s2;
    this.equipaDeArbitros = equipaDeArbitros;
    this.campeonato = campeonato;
  }

  public EstatisticaJogoDto getStats() {
    return this.stats;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
    if (this.s1 != null) {
      s1.setId(id);
    }
    if (this.s2 != null) {
      s2.setId(id);
    }
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

  public List<ArbitroPostDto> getEquipaDeArbitros() {
    return equipaDeArbitros;
  }

  public void setEquipaDeArbitros(List<ArbitroPostDto> equipaDeArbitros) {
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

  public Long getEquipaVencedora() {
    return equipaVencedora;
  }

  public void setEquipaVencedora(Long equipaVencedora) {
    this.equipaVencedora = equipaVencedora;
  }

  public Long getCampeonato() {
    return campeonato;
  }

  public void setCampeonato(Long campeonato) {
    this.campeonato = campeonato;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((estadoDeJogo == null) ? 0 : estadoDeJogo.hashCode());
    result = prime * result + ((localDto == null) ? 0 : localDto.hashCode());
    result = prime * result + ((diaEHora == null) ? 0 : diaEHora.hashCode());
    result = prime * result + ((s1 == null) ? 0 : s1.hashCode());
    result = prime * result + ((s2 == null) ? 0 : s2.hashCode());
    result = prime * result + ((equipaDeArbitros == null) ? 0 : equipaDeArbitros.hashCode());
    result = prime * result + ((stats == null) ? 0 : stats.hashCode());
    result = prime * result + ((equipaVencedora == null) ? 0 : equipaVencedora.hashCode());
    result = prime * result + ((campeonato == null) ? 0 : campeonato.hashCode());
    return result;
  }

  private boolean matchListaArbitros(List<ArbitroPostDto> mine, List<ArbitroPostDto> other) {
    if (mine == null) {
      if (other != null) return false;
    }
    if (other == null) {
      return false;
    }
    if (mine.size() != other.size()) {
      return false;
    }

    for (int i = 0; i < mine.size(); i++) {
      if (mine.get(i).getUtilizador().getId().equals(other.get(i).getUtilizador().getId())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    JogoDto other = (JogoDto) obj;
    if (estadoDeJogo != other.estadoDeJogo) return false;
    if (localDto == null) {
      if (other.localDto != null) return false;
    } else if (!localDto.equals(other.localDto)) return false;
    if (diaEHora == null) {
      if (other.diaEHora != null) return false;
    } else if (!diaEHora.equals(other.diaEHora)) return false;
    if (s1 == null) {
      if (other.s1 != null) return false;
    } else if (!s1.equals(other.s1)) return false;
    if (s2 == null) {
      if (other.s2 != null) return false;
    } else if (!s2.equals(other.s2)) return false;
    if (equipaDeArbitros == null) {
      if (other.equipaDeArbitros != null) return false;
    } else if (!matchListaArbitros(equipaDeArbitros, other.equipaDeArbitros)) return false;
    if (stats == null) {
      if (other.stats != null) return false;
    } else if (!stats.equals(other.stats)) return false;
    if (equipaVencedora == null) {
      if (other.equipaVencedora != null) return false;
    } else if (!equipaVencedora.equals(other.equipaVencedora)) return false;
    if (campeonato == null) {
      if (other.campeonato != null) return false;
    } else if (!campeonato.equals(other.campeonato)) return false;
    return true;
  }
}
