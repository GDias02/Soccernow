package pt.ul.fc.css.soccernow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.campeonatos.QCampeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long>,
    QuerydslPredicateExecutor<Campeonato>, QuerydslBinderCustomizer<QCampeonato>  {
    
    @Override
    public default void customize(
        QuerydslBindings bindings, QCampeonato root){
        bindings.bind(String.class)
          .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
