package ku.cs.shop.services.comparator;

import ku.cs.shop.models.Product;

import java.util.Comparator;

public class ProductNewComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return (o1.getTimeProduct().compareTo(o2.getTimeProduct())) * -1;
    }
}
