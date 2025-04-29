package pt.ul.fc.css.soccernow.dto.jogos;

import java.sql.Date;
import java.util.List;

import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Placar;

/**
 * Classe Dto para representar um local usado para jogos.
 * Para:
 * - Criar um novo jogo:
 * - - O atributo id nao deve estar preenchido.
 * - - Os atributos s1 e s2 devem estar preenchidos.
 * - - O atributo equipaDeArbitros deve ter pelo menos 1 elemento, no entanto, o ArbitroDto requer apenas o id.
 * - - O atributo local e diaEHora devem estar preenchidos
 * - Criar um novo jogo (retorno):
 * - - O atributo id tem de estar preenchido.
 * - Registar o resultado de um jogo:
 * - - O atributo id tem de estar preenchido.
 * - - O atributo estadoAtual deve estar preenchido e confirmar EstadoDeJogo.TERMINADO
 * - - O atributo EstatisticaJogoDto deve estar preenchido.
 */
public class JogoDto {

    private Long id;

    private SelecaoDto s1;

    private SelecaoDto s2;

    private List<ArbitroDto> equipaDeArbitros;

    private EstadoDeJogo estadoAtual;

    private LocalDto local;

    private Date diaEHora;

    private String resultadoFinal; //TODO - pode nao ser preciso, para acesso mais rapido ao resultado de um jogo, sem necessidade de carregar todas as suas estatisticas

    private Placar placar; //TODO - pode nao ser preciso

    private EstatisticaJogoDto stats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EstadoDeJogo getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(EstadoDeJogo estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public LocalDto getLocal() {
        return local;
    }

    public void setLocal(LocalDto local) {
        this.local = local;
    }

    public Date getDiaEHora() {
        return diaEHora;
    }

    public void setDiaEHora(Date diaEHora) {
        this.diaEHora = diaEHora;
    }

    public String getResultadoFinal() {
        return resultadoFinal;
    }

    public void setResultadoFinal(String resultadoFinal) {
        this.resultadoFinal = resultadoFinal;
    }

    public Placar getPlacar() {
        return placar;
    }

    public void setPlacar(Placar placar) {
        this.placar = placar;
    }

    public EstatisticaJogoDto getStats() {
        return stats;
    }

    public void setStats(EstatisticaJogoDto stats) {
        this.stats = stats;
    }
}
