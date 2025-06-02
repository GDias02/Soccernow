package pt.ul.fc.css.soccernow.filters;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

public abstract class AbstractPredicate<T> implements IPredicate{
    protected SearchCriteria criteria;
    
    public AbstractPredicate (SearchCriteria criteria){
        this.criteria = criteria;
    }

    protected abstract Class<T> getEntityClass();

    protected abstract String getAlias();

    public BooleanExpression getPredicate(){
        PathBuilder<T> entityPath = new PathBuilder<>(getEntityClass(), getAlias());
        
        if (isNumeric(criteria.getValue().toString())){
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());

            return switch (criteria.getOperation()){
                case ":" -> path.eq(value);
                case ">" ->path.goe(value);
                case "<" -> path.loe(value);
                default -> null;
            };
        } else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")){
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

    protected boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
