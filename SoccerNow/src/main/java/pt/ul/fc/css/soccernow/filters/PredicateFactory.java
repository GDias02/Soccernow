package pt.ul.fc.css.soccernow.filters;

@FunctionalInterface
public interface PredicateFactory<T> {
    AbstractPredicate<T> create(SearchCriteria criteria);
}
