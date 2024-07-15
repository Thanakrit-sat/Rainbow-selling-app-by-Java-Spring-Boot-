package ku.cs.shop.models;

import ku.cs.shop.services.filterer.ConditionFilterer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductList {
    private ArrayList<Product> products;

    public ProductList() { products = new ArrayList<>(); }

    public void addProduct(Product product) { products.add(product); }

    public ArrayList<Product> getProduct() {
        return products;
    }

    public Product getProductByName(String name){
        for (Product product : products){
            if (product.searchName(name)){ return product; }
        }
        return null;
    }

    public Product getProductByIndex(int index){
        return products.get(index);
    }

    public int countProduct(){
        return products.size();
    }

    public String toCSV(){
        String result = "";
        for (Product product: products) {
            result += product.toCSV() + "\n";
        }
        return result;
    }

    public void sort(Comparator<Product> productComparator) {
        Collections.sort(this.products, productComparator);
    }

    public ArrayList<Product> search(ConditionFilterer<Product> filterer) {
        ArrayList<Product> filtered = new ArrayList<>();

        for (Product product : this.products) {
            if (filterer.match(product)) {
                filtered.add(product);
            }
        }

        return filtered;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public double maxPrice() {
        if (this.products.isEmpty()) {
            return 0;
        }

        double maxValue = 0;
        for (Product product : this.products) {
            if (maxValue < product.getPrice()) {
                maxValue = product.getPrice();
            }
        }
        return maxValue;
    }

    public Product getProductByNameProduct(String name) {
        for (Product product : this.products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
}
