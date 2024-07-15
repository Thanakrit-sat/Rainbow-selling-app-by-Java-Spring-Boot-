package ku.cs.shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ku.cs.shop.models.Product;
import ku.cs.MyListener;

public class ProductController {

    @FXML
    private ImageView imageLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label stockLabel;

    @FXML
    private Label nameLabel;
    private Product product;
    private MyListener myListener;

    public void setData(Product product, MyListener myListener){
        this.product = product;
        this.myListener = myListener;
        imageLabel.setImage(new Image("file:"+product.getPath(), true));
        nameLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()));
        stockLabel.setText(String.valueOf(product.getNumProduct()));
        checkStock();
    }

    public void checkStock() {
        if (product.checkStockIsLow()) {
            stockLabel.setText(product.getNumProduct()+"!!");
            stockLabel.setStyle("-fx-text-fill: #A31F1F ;-fx-font-weight: bold;");
        }
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(product);
    }
}
