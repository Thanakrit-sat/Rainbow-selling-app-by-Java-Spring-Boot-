package ku.cs.shop.services.filterer;

import ku.cs.shop.models.Product;

public class ProductOwnerConditionFilterer implements ConditionFilterer<Product>{
    private String storeName;

    public ProductOwnerConditionFilterer(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public boolean match(Product product) {
        return product.getStoreName().equals(storeName);
    }
}
