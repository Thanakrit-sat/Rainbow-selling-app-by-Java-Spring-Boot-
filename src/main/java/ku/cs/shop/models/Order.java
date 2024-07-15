package ku.cs.shop.models;

public class Order {
    private String buyer;
    private String orderProductName;
    private String orderProductImage;
    private String orderTimeSell;
    private int orderAmount;
    private double orderTotalPrice;
    private String statusOrder;
    private String trackNum;
    private String storeName;

    public Order(String buyer, String orderProductName, String orderProductImage,
                 String orderTimeSell, int orderAmount, double orderTotalPrice,
                 String statusOrder, String trackNum, String storeName)
    {
        this.buyer = buyer;
        this.orderProductName = orderProductName;
        this.orderProductImage = orderProductImage;
        this.orderTimeSell = orderTimeSell;
        this.orderAmount = orderAmount;
        this.orderTotalPrice = orderTotalPrice;
        this.statusOrder = statusOrder;
        this.trackNum = trackNum;
        this.storeName = storeName;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getOrderProductName() {
        return orderProductName;
    }

    public String getOrderProductImage() {
        return orderProductImage;
    }

    public String getOrderTimeSell() {
        return orderTimeSell;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public String getTrackNum() {
        return trackNum;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setOrderProductName(String orderProductName) {
        this.orderProductName = orderProductName;
    }

    public void setOrderProductImage(String orderProductImage) {
        this.orderProductImage = orderProductImage;
    }

    public void setOrderTimeSell(String orderTimeSell) {
        this.orderTimeSell = orderTimeSell;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public void setTrackNum(String trackNum) {
        this.trackNum = trackNum;
    }

    public String toCSV() {
        return buyer + "," + orderProductName + "," + orderProductImage + "," +
                orderTimeSell + "," + orderAmount + "," + orderTotalPrice + "," +
                statusOrder + "," + trackNum + "," + storeName;
    }
}
