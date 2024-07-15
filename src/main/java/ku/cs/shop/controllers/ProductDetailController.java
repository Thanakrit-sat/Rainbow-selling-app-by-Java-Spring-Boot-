package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.FXRouter;
import ku.cs.MyListener;
import ku.cs.shop.models.*;
import ku.cs.shop.services.datasource.CommentFileDataSource;
import ku.cs.shop.services.datasource.OrderFileDataSource;
import ku.cs.shop.services.datasource.ProductFileDataSource;
import ku.cs.shop.services.datasource.UserAccountSource;
import ku.cs.shop.services.filterer.CommentNameConditionFilterer;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProductDetailController {
    private MemberAccountList memberAccountList;
    private MemberAccount member;
    private Product product;
    private ProductList products;
    private OrderList orders;
    private ProductFileDataSource productDataSource;
    private UserAccountSource userAccountSource;
    private OrderFileDataSource orderFileDataSource;
    private CommentList commentList;
    private CommentFileDataSource commentFileDataSource;
    double score;

    @FXML private VBox menuBox;
    @FXML private Separator menuSideSeparator;
    @FXML private Separator menuTopSeparator;
    @FXML private Label usernameLabel;
    @FXML private Label storeNameLabel;
    @FXML private Label priceLabel;
    @FXML private Label quantityLabel;
    @FXML private Label productNameLabel;
    @FXML private Label orderLabel;
    @FXML private Label typeLabel;
    @FXML private ImageView imageLabel;
    @FXML private TextField amountTextField;
    @FXML private TextArea commentTextArea;
    @FXML private Button buyButton;
    @FXML private MenuButton satisfactionMenu;
    @FXML private GridPane commentGrid;
    @FXML private Label scoreLabel;
    @FXML private Label amountReviewLabel;


    @FXML
    public void initialize() {
        userAccountSource = new UserAccountSource();
        productDataSource = new ProductFileDataSource();
        orderFileDataSource = new OrderFileDataSource();
        commentFileDataSource = new CommentFileDataSource();
        commentList = commentFileDataSource.readData();
        orders = orderFileDataSource.readData();
        products = productDataSource.readData();
        memberAccountList = userAccountSource.readData();
        String str = (String) FXRouter.getData();
        String[] data = str.split(",");
        member = memberAccountList.getUserAccountByUsername(data[0]);
        product = products.getProductByNameProduct(data[1]);
        usernameLabel.setText(member.getUsername());
        storeNameLabel.setText(product.getStoreName());
        storeNameLabel.setUnderline(true);
        priceLabel.setText(String.valueOf(product.getPrice()));
        quantityLabel.setText(String.valueOf(product.getNumProduct()));
        productNameLabel.setText(product.getName());
        typeLabel.setText(product.getType());
        orderLabel.setText("");
        imageLabel.setImage(new Image("file:" + product.getPath(), true));
        setReviewScore();
        isOutOfStock();
        setCommentList();
        showGrid();
    }

    @FXML
    public void handleDevInfoMenu(MouseEvent event) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ devinfo
            FXRouter.goTo("devinfo");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า devinfo ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด devinfo");
        }
    }

    @FXML
    public void handleLogOutMenu(MouseEvent event) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ login
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด login");
        }
    }

    @FXML
    public void handleMarketMenu(MouseEvent event) {
        try {
            FXRouter.goTo("market", member);
        } catch (IOException e) {
            System.err.println("ไปหน้า market ไม่ได้");
        }
    }

    @FXML
    public void handleMenuButton(MouseEvent event) {
        if(menuBox.isVisible()){
            menuBox.setVisible(false);
            menuTopSeparator.setVisible(false);
            menuSideSeparator.setVisible(false);
        }
        else {
            menuBox.setVisible(true);
            menuTopSeparator.setVisible(true);
            menuSideSeparator.setVisible(true);
        }
    }

    @FXML
    public void handleStartShopMenu(MouseEvent event) {
        try {
            if(member.getStoreName().equals("none")){
                FXRouter.goTo("openshop",member.getUsername());
            }
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ store
            else FXRouter.goTo("store",member.getUsername() + "," + member.getStoreName() + ",none");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า store ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด store");
        }
    }

    @FXML
    public void handleSuggestionMenu(MouseEvent event) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ suggestion
            FXRouter.goTo("suggestion");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า suggestion ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด suggestion");
        }
    }

    @FXML
    public void handleGoToStoreByStoreName(MouseEvent mouseEvent){
        try{
            FXRouter.goTo("store", member.getUsername() + "," + product.getStoreName() + ",none");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า store ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด store");
        }
    }

    @FXML
    public void handleGoToStoreByType(MouseEvent mouseEvent){
        try{
            FXRouter.goTo("store", member.getUsername() + "," + product.getStoreName() + "," + product.getType());
        } catch (IOException e){
            System.err.println("ไปที่หน้า store ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด store");
        }
    }

    @FXML
    public void handleReportButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("report", member.getUsername() + "," + product.getName());
        } catch (IOException e) {
            System.err.println("ไปหน้า report ไม่ได้");
        }
    }

    @FXML
    public void handleBuyButton(ActionEvent actionEvent){
        try {
            int amount = Integer.parseInt(amountTextField.getText());
            if (product.checkCanBuy(amount)) {
                double totalPrice = product.orderPrice(amount);
                orderLabel.setStyle("-fx-text-fill: #00ccaa;");
                orderLabel.setText("Your order is completed. Total price is " + totalPrice + " baht.");
                isOutOfStock();
                orderFileDataSource.addData(new Order(
                        member.getUsername(), product.getName(),
                        product.getPath(), LocalDateTime.now().withSecond(0).withNano(0) + "",
                        amount,totalPrice, "Shipping",
                        "BCM-" + System.currentTimeMillis(), product.getStoreName()));
            } else {
                orderLabel.setStyle("-fx-text-fill: #ff546b;");
                orderLabel.setText("Invalid amount!!");
                return;
            }
            quantityLabel.setText(String.valueOf(product.getNumProduct()));
            productDataSource.writeData(products);
        } catch (NumberFormatException e){
            orderLabel.setStyle("-fx-text-fill: #ff546b;");
            orderLabel.setText("Invalid amount!!");
            return;
        }
    }

    @FXML
    public void handleCommentButton(ActionEvent actionEvent) {
        if (commentTextArea.getText().isBlank()) {
            return;
        }

        commentFileDataSource.addData(new Commentator(
                member.getUsername(), commentTextArea.getText().trim(),
                score, product.getName()));

        commentTextArea.setText("");
        satisfactionMenu.setText("satisfaction");
        refreshComment();
        return;
    }


    @FXML
    void point1Menu(ActionEvent event) {
        satisfactionMenu.setText("1");
        score = 1;
    }

    @FXML
    void point2Menu(ActionEvent event) {
        satisfactionMenu.setText("2");
        score = 2;
    }

    @FXML
    void point3Menu(ActionEvent event) {
        satisfactionMenu.setText("3");
        score = 3;
    }

    @FXML
    void point4Menu(ActionEvent event) {
        satisfactionMenu.setText("4");
        score = 4;
    }

    @FXML
    void point5Menu(ActionEvent event) {
        satisfactionMenu.setText("5");
        score = 5;
    }

    public void isOutOfStock() {
        if (product.getNumProduct() == 0) {
            buyButton.setText("Sold out");
            amountTextField.setText("");
            buyButton.setDisable(true);
            amountTextField.setDisable(true);
        }
    }

    public void setReviewScore() {
        scoreLabel.setText(String.format("%.2f",commentList.getReviewScoreByProductName(product.getName())));
        amountReviewLabel.setText(String.valueOf(commentList.getAmountByProductName(product.getName())));
    }

    public void setCommentList() {
        commentList.setCommentList(commentList.search(new CommentNameConditionFilterer(product.getName())));
    }

    public void showGrid(){
        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < commentList.countComments(); i++){
                Commentator commentator = commentList.getCommentByIndex(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/comment.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                CommentController commentController = fxmlLoader.getController();
                commentController.setData(commentator);

                if(column == 3){
                    column = 0;
                    row++;
                }

                commentGrid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshComment() {
        commentList = commentFileDataSource.readData();
        setReviewScore();
        commentList.setCommentList(commentList.search(new CommentNameConditionFilterer(product.getName())));
        showGrid();
    }

    public void onMouse1(MouseEvent event) {
        storeNameLabel.setStyle("-fx-text-fill: #6495ED; -fx-cursor: HAND");
    }

    public void exitMouse1(MouseEvent mouseEvent) {
        storeNameLabel.setStyle("-fx-text-fill: #000000;");
    }

    public void onMouse2(MouseEvent event) {
        typeLabel.setStyle("-fx-text-fill: #6495ED; -fx-cursor: HAND");
    }

    public void exitMouse2(MouseEvent mouseEvent) {
        typeLabel.setStyle("-fx-text-fill: #000000;");
    }
}
