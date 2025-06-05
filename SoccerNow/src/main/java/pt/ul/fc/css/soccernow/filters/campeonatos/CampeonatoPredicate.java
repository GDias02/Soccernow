package pt.ul.fc.css.soccernow.filters.campeonatos;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.campeonatos.EstadoCampeonato;
import pt.ul.fc.css.soccernow.entities.campeonatos.QCampeonato;
import pt.ul.fc.css.soccernow.filters.AbstractPredicate;
import pt.ul.fc.css.soccernow.filters.SearchCriteria;

public class CampeonatoPredicate extends AbstractPredicate<Campeonato> {

  public CampeonatoPredicate(SearchCriteria criteria) {
    super(criteria);
  }

  public BooleanExpression getPredicate() {
    PathBuilder<Campeonato> entityPath = new PathBuilder<>(getEntityClass(), getAlias());

    if ("temEquipa".equals(criteria.getKey())) {
      Long equipaId = Long.parseLong(criteria.getValue().toString());
      QCampeonato campeonato = QCampeonato.campeonato;

      return campeonato.equipas.any().id.eq(equipaId);
    }

    if ("numJogosPassado".equals(criteria.getKey())) {
      Integer numJogos = Integer.parseInt(criteria.getValue().toString());
      QCampeonato campeonato = QCampeonato.campeonato;

      JPQLQuery<Long> countSubquery =
          JPAExpressions.select(campeonato.count())
              .from(campeonato)
              .where(campeonato.estado.eq(EstadoCampeonato.TERMINADO));

      return switch (criteria.getOperation()) {
        case ":" -> countSubquery.eq((long) numJogos);
        case ">" -> countSubquery.gt((long) numJogos);
        case "<" -> countSubquery.lt((long) numJogos);
        default -> null;
      };
    }

    if ("numJogosFuturo".equals(criteria.getKey())) {
      Integer numJogos = Integer.parseInt(criteria.getValue().toString());
      QCampeonato campeonato = QCampeonato.campeonato;

      JPQLQuery<Long> countSubquery =
          JPAExpressions.select(campeonato.count())
              .from(campeonato)
              .where(
                  campeonato
                      .estado
                      .eq(EstadoCampeonato.AGENDADO)
                      .or(campeonato.estado.eq(EstadoCampeonato.A_DECORRER)));

      return switch (criteria.getOperation()) {
        case ":" -> countSubquery.eq((long) numJogos);
        case ">" -> countSubquery.gt((long) numJogos);
        case "<" -> countSubquery.lt((long) numJogos);
        default -> null;
      };
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
  protected Class<Campeonato> getEntityClass() {
    return Campeonato.class;
  }

  @Override
  protected String getAlias() {
    return "campeonato";
  }
}
