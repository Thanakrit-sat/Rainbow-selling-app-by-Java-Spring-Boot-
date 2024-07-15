package ku.cs.shop.services.filterer;

import ku.cs.shop.models.Commentator;

public class CommentNameConditionFilterer implements ConditionFilterer<Commentator>{
    private String name;

    public CommentNameConditionFilterer(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean match(Commentator commentator) {
        return commentator.getToProductName().equals(name);
    }
}
