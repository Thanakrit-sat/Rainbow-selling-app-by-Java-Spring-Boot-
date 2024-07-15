package ku.cs.shop.services.filterer;

import ku.cs.shop.models.Product;

public class ProductPriceConditionFilterer implements ConditionFilterer<Product> {
    private double lowerPrice;
    private double upperPrice;

    public ProductPriceConditionFilterer(double lowerPrice, double upperPrice) {
        this.lowerPrice = lowerPrice;
        this.upperPrice = upperPrice;
    }

    public void setPrice(double lowerPrice, double upperPrice) {
        this.lowerPrice = lowerPrice;
        this.upperPrice = upperPrice;
    }

    @Override
    public boolean match(Product product) {
        return (product.getPrice() >= this.lowerPrice &&
                product.getPrice() <= this.upperPrice);
    }
}
