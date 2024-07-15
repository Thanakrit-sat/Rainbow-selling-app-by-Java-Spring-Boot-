package ku.cs.shop.services.filterer;

public interface ConditionFilterer<T> {
    boolean match(T t);
}
