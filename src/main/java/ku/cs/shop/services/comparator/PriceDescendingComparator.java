package ku.cs.shop.services.comparator;

import ku.cs.shop.models.Product;

import java.util.Comparator;

public class PriceDescendingComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getPrice() < o2.getPrice())
            return 1;
        if (o1.getPrice() > o2.getPrice())
            return -1;
        return 0;
    }
}
