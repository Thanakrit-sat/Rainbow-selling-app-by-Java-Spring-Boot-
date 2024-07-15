package ku.cs.shop.models;

public class Product {

    private String name;
    private double price;
    private int numProduct;
    private String imageSrc;
    private String path;
    private String timeProduct;
    private String storeName;
    private int numLowProduct;
    private String type;

    public Product(String name, double price, int numProduct, String path, String timeProduct, String storeName, int numLowProduct, String type){
        this.name = name;
        this.price = price;
        this.numProduct = numProduct;
        this.path = path;
        this.timeProduct = timeProduct;
        this.storeName = storeName;
        this.numLowProduct = numLowProduct;
        this.type = type;
    }

    public Product() {}

    public String getName() {
        return name;
    }

    public double getPrice() { return price; }

    public int getNumProduct() { return numProduct; }

    public String getImageSrc() { return imageSrc; }

    public String getPath() {
        return path;
    }

    public String getTimeProduct() {
        return timeProduct;
    }

    public String getStoreName() { return storeName; }

    public String getType() { return type; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumProduct(int numProduct) {
        this.numProduct = numProduct;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public void setNumLowProduct(int numLowProduct) {
        this.numLowProduct = numLowProduct;
    }

    public int getNumLowProduct() {
        return numLowProduct;
    }

    public boolean searchName(String name) {
        return this.name.trim().equals(name.trim());
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean checkStockIsLow() {
        if (this.numProduct <= this.numLowProduct) {
            return true;
        }
        return false;
    }

    public boolean checkCanBuy(int amount){
        if (amount > numProduct || amount <= 0){
            return false;
        } else{
            return true;
        }
    }

    public double orderPrice(int amount){
        double totalPrice = amount * price;
        numProduct -= amount;
        return totalPrice;
    }

    @Override
    public String toString() {
        return "[" + numProduct + "]  " + name + "   " + price + " bath";
    }

    public String toCSV(){
        return name + "," + price + "," + numProduct + "," + path + "," + timeProduct + "," + storeName + "," + numLowProduct + "," + type;
    }
}
