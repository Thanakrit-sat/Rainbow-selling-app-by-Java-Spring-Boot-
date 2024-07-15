package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ku.cs.FXRouter;
import ku.cs.shop.models.*;
import ku.cs.shop.services.datasource.ProductFileDataSource;
import ku.cs.shop.services.datasource.ReportFileDataSource;
import ku.cs.shop.services.datasource.UserAccountSource;

import java.io.IOException;

public class ReportController {

    @FXML private ScrollPane scrollPane;
    @FXML private Label storeNameLabel;
    @FXML private MenuButton problemTypeMenu;
    @FXML private TextArea textArea;
    @FXML private VBox menuBox;
    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;
    @FXML private Separator menuSideSeparator;
    @FXML private Separator menuTopSeparator;


    private MemberAccount member;
    private UserAccountSource dataSource;
    private MemberAccountList userList;
    private ReportFileDataSource reportFileDataSource;
    private ProductFileDataSource productFileDataSource;
    private ReportList reportList;
    private ProductList products;
    private Product product;
    private String problemType;


    @FXML
    public void initialize() {
        String info = (String) FXRouter.getData();
        String[] data = info.split(",");
        dataSource = new UserAccountSource();
        reportFileDataSource = new ReportFileDataSource();
        productFileDataSource = new ProductFileDataSource();
        products = productFileDataSource.readData();
        reportList = reportFileDataSource.readData();
        userList = dataSource.readData();
        member = userList.getUserAccountByUsername(data[0].trim());
        product = products.getProductByName(data[1].trim());

        storeNameLabel.setText(product.getStoreName());
        usernameLabel.setText(member.getUsername());
        statusLabel.setText("");
        problemType = "problem type";
    }


    @FXML
    public void imageProblemMenu(ActionEvent event) {
        problemType = "inappropriate image";
        problemTypeMenu.setText("inappropriate image");
    }

    @FXML
    public void productProblemMenu(ActionEvent event) {
        problemType = "inappropriate product";
        problemTypeMenu.setText("inappropriate product");
    }

    @FXML
    public void handleSendButton(ActionEvent event) {
        if (textArea.getText().isBlank()) {
            statusLabel.setText("Please complete the information");
            return;
        }

        if (problemType.equals("problem type")) {
            statusLabel.setText("Please select a title");
            return;
        }

        //complete collect data
        reportFileDataSource.addData(new Reporter(
                member.getUsername(),
                problemType,
                textArea.getText(),
                product.getStoreName(),
                product.getName()));
        statusLabel.setText("sent");
        statusLabel.setStyle("-fx-text-fill: #2ECC71;");
        return;
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
        }else {
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
            else FXRouter.goTo("store",member.getUsername() + "," + member.getStoreName()+",none");
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

}
