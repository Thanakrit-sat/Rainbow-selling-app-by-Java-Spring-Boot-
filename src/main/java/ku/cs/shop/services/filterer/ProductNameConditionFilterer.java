package ku.cs.shop.services.filterer;

import ku.cs.shop.models.Product;

public class ProductNameConditionFilterer implements ConditionFilterer<Product> {
    private String name;

    public ProductNameConditionFilterer(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean match(Product product) {
        return product.getName().toLowerCase().contains(this.name.toLowerCase());
    }
}
