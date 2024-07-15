package ku.cs.shop.services.comparator;

import ku.cs.shop.models.Order;

import java.util.Comparator;

public class OrderNewComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return (o1.getOrderTimeSell().compareTo(o2.getOrderTimeSell()) * -1);
    }
}
