package ku.cs.shop.models;

import ku.cs.shop.services.filterer.ConditionFilterer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrderList {
    private ArrayList<Order> orders;

    public OrderList() {
        orders = new ArrayList<>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void sort(Comparator<Order> orderComparator) {
        Collections.sort(orders, orderComparator);
    }

    public ArrayList<Order> search(ConditionFilterer<Order> filterer) {
        ArrayList<Order> filtered = new ArrayList<>();

        for (Order order : this.orders) {
            if (filterer.match(order)) {
                filtered.add(order);
            }
        }

        return filtered;
    }


    public String toCSV(){
        String result = "";
        for (Order order : orders) {
            result += order.toCSV() + "\n";
        }
        return result;
    }


}
