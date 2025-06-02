package pt.ul.fc.css.soccernow.filtros;

public class JogadorFiltros {
    
    private String nome;
    private String posicao;
    private String golos;
    private String cartoes;
    private String jogos;

    public JogadorFiltros() {}

    public JogadorFiltros(String nome, String posicao, String golos, String cartoes, String jogos) {
        this.nome = nome;
        this.posicao = posicao;
        this.golos = golos;
        this.cartoes = cartoes;
        this.jogos = jogos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getGolos() {
        return golos;
    }

    public void setGolos(String golos) {
        this.golos = golos;
    }

    public String getCartoes() {
        return cartoes;
    }

    public void setCartoes(String cartoes) {
        this.cartoes = cartoes;
    }

    public String getJogos() {
        return jogos;
    }

    public void setJogos(String jogos) {
        this.jogos = jogos;
    }
}
