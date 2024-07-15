package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import ku.cs.FXRouter;
import ku.cs.MyListener;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.datasource.ProductFileDataSource;
import ku.cs.shop.services.datasource.UserAccountSource;
import ku.cs.shop.services.comparator.PriceAscendingComparator;
import ku.cs.shop.services.comparator.PriceDescendingComparator;
import ku.cs.shop.services.comparator.ProductNewComparator;
import ku.cs.shop.services.filterer.ProductNameConditionFilterer;
import ku.cs.shop.services.filterer.ProductOwnerConditionFilterer;
import ku.cs.shop.services.filterer.ProductPriceConditionFilterer;
import ku.cs.shop.services.filterer.ProductTypeConditionFilterer;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.VBox.setMargin;

public class StoreController {
    @FXML
    private Label usernameLabel;
    private String username;
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
    private MemberAccountList memberAccountList;
    private UserAccountSource userAccountSource;
    private String storeName;
    private String type;

    @FXML private HBox addProductBox;
    @FXML private HBox editProductBox;
    @FXML private HBox checkOrderBox;
    @FXML private Separator underStoreSeparator;
    @FXML private HBox suggestionBox;
    @FXML private HBox blankBox;

    private MyListener myListener;

    @FXML
    public void initialize() {
        userAccountSource = new UserAccountSource();
        productDataSource = new ProductFileDataSource();
        memberAccountList = userAccountSource.readData();
        String str = (String) FXRouter.getData();
        String[] data = str.split(",");
        member = memberAccountList.getUserAccountByUsername(data[0]);
        storeName = data[1];
        type = data[2];
        usernameLabel.setText(member.getUsername());
        productDataSource = new ProductFileDataSource("csv-database", "products.csv");
        products = productDataSource.readData();
        if (type.equals("none")){ getProductStoreName(); }
        else { getProductType(); }
        products.sort(new ProductNewComparator());
        lowerPriceTextField.setText("0");
        upperPriceTextField.setText("" + products.maxPrice());
        showGrid();
        checkStoreOwner();
    }


    public void checkStoreOwner(){
        if(!member.getStoreName().equals(storeName)){
            addProductBox.setVisible(false);
            editProductBox.setVisible(false);
            checkOrderBox.setVisible(false);
            setMargin(underStoreSeparator, new Insets(-102, 0, 0, 0));
            setMargin(suggestionBox, new Insets (0,0,0,0));
            blankBox.setPrefHeight(380);
        }
    }

    public void showGrid(){
        myListener = new MyListener() {
            @Override
            public void onClickListener(Product product) { selectedProduct(product); }
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
    public void handleHomeMenu(MouseEvent mouseEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ store
            FXRouter.goTo("market");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด home");
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
    public void handleAddProductMenu(MouseEvent mouseEvent){
        try {
            FXRouter.goTo("addproduct", member);
        } catch(IOException e) {
            System.err.println("ไปที่หน้า addproduct ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด addproduct");
        }
    }

    @FXML void handleCheckOrder(MouseEvent mouseEvent) {
        try {
            FXRouter.goTo("ordercheck", member);
        } catch(IOException e) {
            System.err.println("ไปหน้า check order ไม่ได้");
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

    public void getProductStoreName() {
        products.setProducts(products.search(new ProductOwnerConditionFilterer(storeName)));
    }

    public void getProductType(){
        products.setProducts(products.search(new ProductTypeConditionFilterer(type)));
    }

    public void resetProduct() {
        products = productDataSource.readData();
        getProductStoreName();
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

    @FXML
    public void handleProductEditing(MouseEvent event) {
        try {
            FXRouter.goTo("productedit", member);
        } catch (IOException e) {
            System.err.println("ไป productedit ไม่ได้");
        }
    }
}
