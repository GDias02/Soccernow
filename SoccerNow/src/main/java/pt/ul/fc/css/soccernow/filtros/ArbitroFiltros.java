package pt.ul.fc.css.soccernow.filtros;

public class ArbitroFiltros {
    
    private String nome;
    private String jogos;
    private String cartoes;

    public ArbitroFiltros() {}

    public ArbitroFiltros(String nome, String jogos, String cartoes) {
        this.nome = nome;
        this.jogos = jogos;
        this.cartoes = cartoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getJogos() {
        return jogos;
    }

    public void setJogos(String jogos) {
        this.jogos = jogos;
    }

    public String getCartoes() {
        return cartoes;
    }

    public void setCartoes(String cartoes) {
        this.cartoes = cartoes;
    }
}
