package ku.cs.shop.models;

public class Reporter {
    private String reporter;
    private String title;
    private String text;
    private String receiver;
    private String productName;

    public Reporter(String reporter, String title, String text, String receiver, String productName) {
        this.reporter = reporter;
        this.title = title;
        this.text = text;
        this.receiver = receiver;
        this.productName = productName;
    }

    public String getReporter() {
        return reporter;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getProductName() {
        return productName;
    }


    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStoreName(String storeName) {
        this.receiver = storeName;
    }

    public String toCSV() {
        return reporter + "," + title + "," + text + "," + receiver + "," + productName;
    }
}
