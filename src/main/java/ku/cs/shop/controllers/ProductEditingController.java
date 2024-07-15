package ku.cs.shop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ku.cs.FXRouter;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.datasource.ProductFileDataSource;
import ku.cs.shop.services.comparator.ProductNewComparator;
import ku.cs.shop.services.filterer.ProductOwnerConditionFilterer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class ProductEditingController {

    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;

    @FXML private ImageView imageProduct;
    @FXML private TextField nameProductTextField;
    @FXML private TextField priceProductTextField;
    @FXML private TextField quantityProductTextField;
    @FXML private TextField lowQuantityTextField;
    @FXML private VBox menuBox;
    @FXML private Label usernameLabel;
    @FXML private Button updateButton;
    @FXML private Button uploadButton;
    @FXML private ScrollPane scrollPane;

    private MemberAccount member;
    private Product selectedProduct;
    private Product product;
    private ProductList products;
    private ProductFileDataSource productDataSource;
    private ObservableList<Product> productObservableList;
    private ProductList productsForUpdate;
    private String imagePath;


    @FXML
    public void initialize() {
        member = (MemberAccount) FXRouter.getData();
        usernameLabel.setText(member.getUsername());
        imageProduct.setImage(new Image("file:"+"imagesProduct/productDefault.png",true));
        productDataSource = new ProductFileDataSource("csv-database", "products.csv");
        products = productDataSource.readData();
        getProductStoreName();
        products.sort(new ProductNewComparator());
        clearSelected();
        showProductData();
        productTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedProduct(newValue);
            }
        });
    }

    private void clearSelected() {
        nameProductTextField.setText("");
        priceProductTextField.setText("");
        quantityProductTextField.setText("");
        lowQuantityTextField.setText("");
        imageProduct.setImage(new Image("file:"+"imagesProduct/productDefault.png",true));
        updateButton.setDisable(true);
        uploadButton.setDisable(true);
    }

    private void showSelectedProduct(Product newValue) {
        selectedProduct = newValue;
        imagePath = selectedProduct.getPath();
        nameProductTextField.setText(selectedProduct.getName());
        priceProductTextField.setText(selectedProduct.getPrice() + "");
        quantityProductTextField.setText(selectedProduct.getNumProduct() + "");
        imageProduct.setImage(new Image("file:"+selectedProduct.getPath(), true));
        // test
        lowQuantityTextField.setText(selectedProduct.getNumLowProduct()+"");
        updateButton.setDisable(false);
        uploadButton.setDisable(false);
    }

    private void showProductData() {
        productObservableList = FXCollections.observableArrayList(products.getProduct());
        productTableView.setItems(productObservableList);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("numProduct"));
    }

    public void getProductStoreName() {
        products.setProducts(products.search(new ProductOwnerConditionFilterer(member.getStoreName())));
    }

    @FXML
    public void clickToProfile(MouseEvent event) {
        try {
            FXRouter.goTo("profile", member.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
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
    public void handleAddProductMenu(MouseEvent event) {
        try {
            FXRouter.goTo("addproduct", member);
        } catch(IOException e) {
            System.err.println("ไปที่หน้า addproduct ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด addproduct");
        }
    }

    @FXML
    public void handleChangeImageButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG","*.png", "*.jpg", "*.jpeg" ));

        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());

        if (file != null) {
            try {
                File destDir = new File("imagesProduct");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_"+ System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
                );

                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // set image
                imageProduct.setImage(new Image(target.toUri().toString()));
                // set path image to imagePath
                imagePath = destDir + "/" + filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    public void handleHomeMenu(MouseEvent event) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ store
            FXRouter.goTo("market", member);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด home");
        }
    }

    @FXML
    public void handleYourStore(MouseEvent event) {
        try {
            FXRouter.goTo("store", member.getUsername() + "," +member.getStoreName() + ",none");
        } catch (IOException e) {
            System.err.println("ไปหน้า store ไม่ได้");
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
    public void handleMenuButton(MouseEvent event) {
        if(menuBox.isVisible()){
            menuBox.setVisible(false);
            scrollPane.setPrefWidth(800);
            scrollPane.setLayoutX(0);
        }
        else {
            menuBox.setVisible(true);
            scrollPane.setPrefWidth(640);
            scrollPane.setLayoutX(160);
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
    public void handleUpdateButton(ActionEvent event) {
        if (checkInformation(nameProductTextField.getText().trim(), priceProductTextField.getText().trim(),
                        quantityProductTextField.getText().trim(),  lowQuantityTextField.getText().trim())) {
            return;
        }
        productsForUpdate = productDataSource.readData();
        product = productsForUpdate.getProductByNameProduct(selectedProduct.getName());
        product.setName(nameProductTextField.getText().trim());
        product.setPrice(Double.parseDouble(priceProductTextField.getText().trim()));
        product.setNumProduct(Integer.parseInt(quantityProductTextField.getText().trim()));
        product.setNumLowProduct(Integer.parseInt(lowQuantityTextField.getText().trim()));
        // ลบรูปเก่า
        if (!(imagePath.equals(product.getPath()))) {
            deleteFilePicture(product.getPath());
        }
        // update new image
        product.setPath(imagePath);
        clearSelected();
        productTableView.refresh();
        productTableView.getSelectionModel().clearSelection();
        //update csv
        productDataSource.writeData(productsForUpdate);
        refreshProduct();
    }

    public boolean checkInformation(String name, String priceStr, String numProductStr, String numLowProductStr) {
        if (name.isBlank() || priceStr.isBlank() || numLowProductStr.isBlank() || numProductStr.isBlank()) {
            return true;
        }
        double price;
        int numProduct;
        int numLowProduct;
        try {
            price = Double.parseDouble(priceStr);
            numLowProduct = Integer.parseInt(numLowProductStr);
            numProduct = Integer.parseInt(numProductStr);
        } catch (NumberFormatException e) {
            System.err.println("ไม่ใช่ตัวเลข");
            return true;
        }
        if (price > 0 && numProduct > 0 && numLowProduct > 0) {
            return false;
        }
        return true;
    }

    public void refreshProduct() {
        products = productDataSource.readData();
        getProductStoreName();
        products.sort(new ProductNewComparator());
        showProductData();
    }

    public void deleteFilePicture(String filename) {

        if (filename.equals("imagesProduct/productDefault.png")) {
            System.out.println(filename);
            return;
        }

        File file = new File(filename);
        if (file.delete()) {
            System.out.println("File " + filename + " deleted successfully");
        }
        else {
            System.out.println("Failed to delete the " + filename );
        }

    }

}
