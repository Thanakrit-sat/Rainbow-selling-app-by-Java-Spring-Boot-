package ku.cs.shop.services.filterer;

import ku.cs.shop.models.Product;

public class ProductTypeConditionFilterer implements ConditionFilterer<Product> {
    private String type;

    public ProductTypeConditionFilterer(String type){ this.type = type; }

    public void setType(String type) { this.type = type; }

    @Override
    public boolean match(Product product) {
        return product.getType().equals(type);
    }
}
