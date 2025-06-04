package pt.ul.fc.css.soccernow.filters.campeonatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.campeonatos.QCampeonato;
import pt.ul.fc.css.soccernow.entities.equipas.QEquipa;
import pt.ul.fc.css.soccernow.filters.AbstractPredicate;
import pt.ul.fc.css.soccernow.filters.PredicateFactory;
import pt.ul.fc.css.soccernow.filters.SearchCriteria;

public class CampeonatoPredicate extends AbstractPredicate<Campeonato> {
    protected final List<SearchCriteria> params = new ArrayList<>();

    public CampeonatoPredicate(SearchCriteria criteria){
        super(criteria);
    }

    /**
     * Filtros em falta: numJogos realizados, num Jogos a realizar
     */
    public BooleanExpression build(PredicateFactory<Campeonato> factory) {
        if (params.isEmpty()) {
            return null;
        }

        if ("temEquipa".equals(criteria.getKey())) {
            Long equipaId = Long.parseLong(criteria.getValue().toString());
            QCampeonato campeonato = QCampeonato.campeonato;

            return campeonato.equipas.any().id.eq(equipaId);
        }

        List<BooleanExpression> predicates = params.stream()
            .map(factory::create)
            .map(AbstractPredicate::getPredicate)
            .filter(Objects::nonNull)
            .toList();

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }

        return result;
    }

    @Override
    protected Class<Campeonato> getEntityClass(){
        return Campeonato.class;
    }

    @Override
    protected String getAlias(){
        return "campeonato";
    }
}
