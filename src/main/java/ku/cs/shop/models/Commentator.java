package ku.cs.shop.models;

public class Commentator {
    private String name;
    private String text;
    private double score;
    private String toProductName;

    public Commentator(String name, String text, double score, String toProductName) {
        this.name = name;
        this.text = text;
        this.score = score;
        this.toProductName = toProductName;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public double getScore() {
        return score;
    }

    public String getToProductName() {
        return toProductName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setToProductName(String toProductName) {
        this.toProductName = toProductName;
    }

    public String toCSV() {
        return name + "," + text + "," + score + "," + toProductName;
    }
}
