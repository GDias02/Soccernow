package pt.ul.fc.css.soccernow.filters.equipas;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.equipas.QConquista;
import pt.ul.fc.css.soccernow.entities.equipas.QEquipa;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.QJogo;
import pt.ul.fc.css.soccernow.entities.jogos.QSelecao;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.entities.utilizadores.QJogador;
import pt.ul.fc.css.soccernow.filters.AbstractPredicate;
import pt.ul.fc.css.soccernow.filters.SearchCriteria;

public class EquipaPredicate extends AbstractPredicate<Equipa>{
    
    public EquipaPredicate(SearchCriteria criteria){
        super(criteria);
    }

    @Override
    public BooleanExpression getPredicate() {
        PathBuilder<Equipa> entityPath = new PathBuilder<>(getEntityClass(), getAlias());

        /**  Número de Jogadores */
        if ("numJogadores".equals(criteria.getKey())) {
            QEquipa equipa = QEquipa.equipa;
            return switch (criteria.getOperation()) {
                case ">" -> equipa.jogadores.size().goe(Integer.parseInt(criteria.getValue().toString()) + 1);
                case "<" -> equipa.jogadores.size().loe(Integer.parseInt(criteria.getValue().toString()) - 1);
                case ":" -> equipa.jogadores.size().eq(Integer.parseInt(criteria.getValue().toString()));
                default -> null;
            };
        }

        /** Número de Vitórias/Empates/Derrotas */
        if ("numVitorias".equals(criteria.getKey())) {
            int valor = Integer.parseInt(criteria.getValue().toString());

            QEquipa equipa = QEquipa.equipa;
            QJogo jogo = QJogo.jogo;

            // Subquery: SELECT COUNT(*) FROM jogo WHERE equipaVencedora = equipa
            JPQLQuery<Long> countSubquery = JPAExpressions
                .select(jogo.count())
                .from(jogo)
                .where(jogo.placar.equipaVencedora.eq(equipa.id));

            return switch (criteria.getOperation()) {
                case ":" -> countSubquery.eq((long) valor);
                case ">" -> countSubquery.gt((long) valor);
                case "<" -> countSubquery.lt((long) valor);
                default -> null;
            };
        }

        if ("numEmpates".equals(criteria.getKey())) {
            int valor = Integer.parseInt(criteria.getValue().toString());

            QEquipa equipa = QEquipa.equipa;
            QJogo jogo = QJogo.jogo;
            QSelecao selecao = QSelecao.selecao;

            // Subquery: SELECT COUNT(*) FROM jogo WHERE equipaVencedora = equipa
            JPQLQuery<Long> countSubquery = JPAExpressions
                .select(jogo.count())
                .from(jogo)
                .join(jogo.selecoes, selecao)
                .where(jogo.placar.equipaVencedora.eq(0L)
                    .and(jogo.estadoDeJogo.eq(EstadoDeJogo.TERMINADO)),
                    selecao.equipa.id.eq(equipa.id));

            return switch (criteria.getOperation()) {
                case ":" -> countSubquery.eq((long) valor);
                case ">" -> countSubquery.gt((long) valor);
                case "<" -> countSubquery.lt((long) valor);
                default -> null;
            };
        }

        if ("numDerrotas".equals(criteria.getKey())) {
            int valor = Integer.parseInt(criteria.getValue().toString());

            QEquipa equipa = QEquipa.equipa;
            QJogo jogo = QJogo.jogo;
            QSelecao selecao = QSelecao.selecao;

            // Subquery: SELECT COUNT(*) FROM jogo WHERE equipaVencedora = equipa
            JPQLQuery<Long> countSubquery = JPAExpressions
                .select(jogo.count())
                .from(jogo)
                .join(jogo.selecoes, selecao)
                .where(jogo.placar.equipaVencedora.ne(equipa.id)
                    .and(jogo.placar.equipaVencedora.ne(0L))
                    .and(jogo.estadoDeJogo.eq(EstadoDeJogo.TERMINADO)),
                    selecao.equipa.id.eq(equipa.id));

            return switch (criteria.getOperation()) {
                case ":" -> countSubquery.eq((long) valor);
                case ">" -> countSubquery.gt((long) valor);
                case "<" -> countSubquery.lt((long) valor);
                default -> null;
            };
        }

        /** Get Equipa with a given conquista */
        if ("conquista".equals(criteria.getKey())){
            Long conquistaId = Long.parseLong(criteria.getValue().toString());
            
            QConquista conquista = QConquista.conquista;
            
            return JPAExpressions
                .select(conquista.equipa)
                .from(conquista)
                .where(conquista.id.eq(conquistaId))
                .exists();
        }

        /** Get Equipas que não têm determinadas posições no plantel */
        if ("semPosicao".equals(criteria.getKey())){
            Posicao pos = Posicao.valueOf(criteria.getValue().toString());

            QEquipa equipa = QEquipa.equipa;
            QJogador jogador = QJogador.jogador;

            // Subquery: existem jogadores nesta equipa com essa posição?
            return JPAExpressions
                .selectOne()
                .from(jogador)
                .where(
                    jogador.posicaoPreferida.eq(pos),
                    equipa.jogadores.contains(jogador)
                )
                .notExists();
        }

        // CASO PADRÃO (campos normais)
        if (isNumeric(criteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            return switch (criteria.getOperation()) {
                case ":" -> path.eq(value);
                case ">" -> path.goe(value);
                case "<" -> path.loe(value);
                default -> null;
            };
        } else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }

        return null;
    }



    @Override
    protected Class<Equipa> getEntityClass(){
        return Equipa.class;
    }

    @Override
    protected String getAlias(){
        return "equipa";
    }
}
