package pt.ul.fc.css.soccernow.filters;

import com.querydsl.core.types.dsl.BooleanExpression;

public interface IPredicate {
    BooleanExpression getPredicate();
    
}
