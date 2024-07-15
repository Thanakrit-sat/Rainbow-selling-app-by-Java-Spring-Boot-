package ku.cs.shop.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.FXRouter;
import ku.cs.MyListener;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.datasource.ProductFileDataSource;
import ku.cs.shop.services.comparator.PriceAscendingComparator;
import ku.cs.shop.services.comparator.PriceDescendingComparator;
import ku.cs.shop.services.comparator.ProductNewComparator;
import ku.cs.shop.services.filterer.ProductNameConditionFilterer;
import ku.cs.shop.services.filterer.ProductPriceConditionFilterer;

import java.io.IOException;
import java.util.ArrayList;

public class
MarketController {

    @FXML private Label usernameLabel;
    private MemberAccount member;
    @FXML private GridPane productGrid;
    private ProductFileDataSource productDataSource;
    @FXML private VBox menuBox;
    @FXML private ScrollPane productGridScrollPane;
    @FXML private Separator menuSideSeparator;
    @FXML private Separator menuTopSeparator;
    @FXML private MenuButton menuButton;
    @FXML private Pane priceRangePane;
    @FXML private TextField lowerPriceTextField;
    @FXML private TextField upperPriceTextField;
    @FXML private TextField searchTextField;
    private ProductList products;

    private MyListener myListener;

    @FXML
    public void initialize() {
        member = (MemberAccount) FXRouter.getData();
        usernameLabel.setText(member.getUsername());
        productDataSource = new ProductFileDataSource("csv-database", "products.csv");
        products = productDataSource.readData();
        products.sort(new ProductNewComparator());
        lowerPriceTextField.setText("0");
        upperPriceTextField.setText("" + products.maxPrice());
        showGrid();
    }

    public void showGrid(){
        myListener = new MyListener() {
            @Override
            public void onClickListener(Product product) {
                selectedProduct(product);
            }
        };

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < products.countProduct(); i++){
                Product product = products.getProductByIndex(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/product.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ProductController productController = fxmlLoader.getController();
                productController.setData(product, myListener);

                if(column == 3){
                    column = 0;
                    row++;
                }

                productGrid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectedProduct(Product product) {
        try {
            FXRouter.goTo("productdetail", member.getUsername() + "," + product.getName());
        } catch (IOException e) {
            System.err.println("ไปไม่ได้");
        }
    }

    @FXML
    public void handleDevInfoMenu(MouseEvent mouseEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ devinfo
            FXRouter.goTo("devinfo");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า devinfo ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด devinfo");
        }
    }

    @FXML
    public void handleSuggestionMenu(MouseEvent mouseEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ suggestion
            FXRouter.goTo("suggestion");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า suggestion ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด suggestion");
        }
    }

    @FXML
    public void handleStartShopMenu(MouseEvent mouseEvent) {
        try {
            if(member.getStoreName().equals("none")){
                FXRouter.goTo("openshop",member.getUsername());
            }
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ store
            else FXRouter.goTo("store",member.getUsername() + "," +member.getStoreName() + ",none");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า store ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด store");
        }
    }

    @FXML
    public void handleLogOutMenu(MouseEvent mouseEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ login
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด login");
        }
    }

    @FXML
    public void handleMenuButton(MouseEvent mouseEvent){
            if(menuBox.isVisible()){
                menuBox.setVisible(false);
                menuTopSeparator.setVisible(false);
                menuSideSeparator.setVisible(false);
                priceRangePane.setVisible(false);
                productGridScrollPane.setPrefWidth(800);
                productGridScrollPane.setLayoutX(0);
            }
            else {
                menuBox.setVisible(true);
                menuTopSeparator.setVisible(true);
                menuSideSeparator.setVisible(true);
                priceRangePane.setVisible(true);
                productGridScrollPane.setPrefWidth(640);
                productGridScrollPane.setLayoutX(160);
            }
        }

    @FXML
    public void handleSortLowToHighPrice(ActionEvent actionEvent) {
        products.sort(new PriceAscendingComparator());
        menuButton.setText("lowest -> highest");
        productGrid.getChildren().clear();
        showGrid();
    }

    @FXML
    public void handleSortHighToLowPrice(ActionEvent actionEvent) {
        products.sort(new PriceDescendingComparator());
        menuButton.setText("highest -> lowest");
        productGrid.getChildren().clear();
        showGrid();
    }

    @FXML
    public void handleSortNewProduct(ActionEvent actionEvent) {
        products.sort(new ProductNewComparator());
        menuButton.setText("new product");
        productGrid.getChildren().clear();
        showGrid();
    }

    @FXML
    public void handlePriceSearchButton(ActionEvent actionEvent) {
        double lowerPrice;
        double upperPrice;
        ArrayList<Product> filtered;
        try {
            lowerPrice = Double.parseDouble(lowerPriceTextField.getText());
            upperPrice = Double.parseDouble(upperPriceTextField.getText());
            resetProduct();
            filtered = products.search(new ProductPriceConditionFilterer(lowerPrice, upperPrice));
            products.setProducts(filtered);
        } catch (NumberFormatException e) {
            return;
        }

        productGrid.getChildren().clear();
        showGrid();
    }

    public void resetProduct() {
        products = productDataSource.readData();
    }

    @FXML
    public void handleRefreshProduct(MouseEvent event) {
        resetProduct();
        menuButton.setText("product type");
        products.sort(new ProductNewComparator());
        productGrid.getChildren().clear();
        lowerPriceTextField.setText("0");
        upperPriceTextField.setText("" + products.maxPrice());
        searchTextField.setText("");
        showGrid();
    }

    @FXML
    public void handleSearchName() {
        String name = searchTextField.getText().trim();
        if (name.isBlank()) {
            return;
        }
        products.setProducts(products.search(new ProductNameConditionFilterer(name)));
        productGrid.getChildren().clear();
        showGrid();
    }

    public void onMouse(MouseEvent event) {
        usernameLabel.setStyle("-fx-text-fill: #6495ED;");
    }

    public void exitMouse(MouseEvent mouseEvent) {
        usernameLabel.setStyle("-fx-text-fill: #000000;");
    }

    public void clickToProfile(MouseEvent mouseEvent) {
        try {
            FXRouter.goTo("profile", member.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}