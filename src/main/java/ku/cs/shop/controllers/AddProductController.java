package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ku.cs.FXRouter;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.datasource.ProductFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddProductController {
    @FXML private Label usernameLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField quantityTextField;
    @FXML private TextField typeTextField;
    private MemberAccount member;
    private ProductFileDataSource productDataSource;
    @FXML private VBox menuBox;
    @FXML private Separator menuSideSeparator;
    @FXML private Separator menuTopSeparator;
    @FXML private Pane priceRangePane;
    @FXML private ImageView productImage;
    @FXML private Label statusLabel;
    private ProductList products;
    private String imagePath;

    @FXML
    public void initialize() {
        member = (MemberAccount) FXRouter.getData();
        usernameLabel.setText(member.getUsername());
        productDataSource = new ProductFileDataSource("csv-database", "products.csv");
        products = productDataSource.readData();
        imagePath = "imagesProduct/productDefault.png";
        productImage.setImage(new Image("file:"+imagePath, true));
        statusLabel.setText("");
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
            FXRouter.goTo("market", member);
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
    public void handleProductEditing(MouseEvent event) {
        try {
            FXRouter.goTo("productedit", member);
        } catch (IOException e) {
            System.err.println("ไป productedit ไม่ได้");
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
    public void handleYourStore(MouseEvent event) {
        try {
            FXRouter.goTo("store", member.getUsername() + "," +member.getStoreName()+ ",none");
        } catch (IOException e) {
            System.err.println("ไปหน้า store ไม่ได้");
        }
    }

    @FXML
    public void handleMenuButton(MouseEvent mouseEvent){
        if(menuBox.isVisible()){
            menuBox.setVisible(false);
            menuTopSeparator.setVisible(false);
            menuSideSeparator.setVisible(false);
            priceRangePane.setVisible(false);
        }
        else {
            menuBox.setVisible(true);
            menuTopSeparator.setVisible(true);
            menuSideSeparator.setVisible(true);
            priceRangePane.setVisible(true);
        }
    }

    @FXML
    public void handleUploadButton(ActionEvent onAction){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG","*.png", "*.jpg", "*.jpeg" ));

        Node source = (Node) onAction.getSource();
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
                productImage.setImage(new Image(target.toUri().toString()));
                // set path image to imagePath
                imagePath = destDir + "/" + filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleAddProductButton(ActionEvent onAction){
        String name = nameTextField.getText();
        String type = typeTextField.getText();
        double price;
        int num;
        if (nameTextField.getText().isBlank() || priceTextField.getText().isBlank() || quantityTextField.getText().isBlank() || typeTextField.getText().isBlank()) {
            statusLabel.setText("Please complete the information");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
            return;
        }
        else if (products.getProductByNameProduct(name) != null) {
            statusLabel.setText("this name has already in the market.");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
            return;
        }
        try {
            price = Double.parseDouble(priceTextField.getText());
            num = Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException e) {
            statusLabel.setText("invalid price or quantity");
            return;
        }

        if (price < 0 || num <= 0) {
            statusLabel.setText("invalid price or quantity");
            return;
        }

        Product newProduct = new Product(name,
                                        price,
                                        num,
                                        imagePath,
                                        String.valueOf(LocalDateTime.now().withNano(0)),
                                        member.getStoreName(),
                            1,
                                        type);
        products.addProduct(newProduct);

        //update products-csv
        productDataSource.writeData(products);

        try {
            FXRouter.goTo("store", member.getUsername() + "," + member.getStoreName() + ",none");
        }catch (IOException e) {
            System.err.println("ไปหน้า store ไม่ได้");
            System.err.println("ไปตรวจ store ซะนะ");
        }
    }


    public void clickToProfile(MouseEvent mouseEvent) {
        try {
            FXRouter.goTo("profile", member.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
