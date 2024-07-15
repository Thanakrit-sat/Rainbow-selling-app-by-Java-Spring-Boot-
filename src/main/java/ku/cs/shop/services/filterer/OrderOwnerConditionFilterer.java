package ku.cs.shop.services.filterer;

import ku.cs.shop.models.Order;

public class OrderOwnerConditionFilterer implements ConditionFilterer<Order> {
    private String storeName;

    public OrderOwnerConditionFilterer(String name) {
        this.storeName = name;
    }

    public void setName(String name) {
        this.storeName = name;
    }

    @Override
    public boolean match(Order order) {
        return order.getStoreName().equals(storeName);
    }
}
