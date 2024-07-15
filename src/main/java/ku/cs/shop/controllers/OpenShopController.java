package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.FXRouter;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.services.datasource.UserAccountSource;

import java.io.IOException;

public class OpenShopController {
    @FXML private Label usernameLabel;
    private String username;
    private MemberAccount member;
    @FXML private VBox menuBox;
    @FXML private Separator menuSideSeparator;
    @FXML private Separator menuTopSeparator;
    @FXML private Pane priceRangePane;
    @FXML private TextField storeNameTextField;
    @FXML private Label statusLabel;

    private String memberUsername;
    private UserAccountSource dataSource;
    private MemberAccountList users;


    @FXML
    public void initialize() {
        memberUsername = (String) FXRouter.getData();
        dataSource = new UserAccountSource();
        users = dataSource.readData();
        member = users.getUserAccountByUsername(memberUsername);
        usernameLabel.setText(member.getUsername());
        statusLabel.setText("");
    }

    @FXML
    public void handleHomeMenu(MouseEvent mouseEvent) {
        try {
            FXRouter.goTo("market",member);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด home");
        }
    }

    @FXML
    public void handleSetStoreNameButton(ActionEvent onAction){
        if(storeNameTextField.getText().isBlank() || storeNameTextField.getText().isEmpty()) {
            statusLabel.setText("Please complete the information");
            return;
        }
        if(!users.checkStoreNameIsEmpty(storeNameTextField.getText().trim())) {
            statusLabel.setText("this name already exists");
            return;
        }
        member.setStoreName(storeNameTextField.getText().trim());
        dataSource.writeData(users);
        try {
            if(member.getStoreName().equals("none")){
                FXRouter.goTo("openshop",member.getUsername());
            }
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ store
            else FXRouter.goTo("store",member.getUsername()+","+member.getStoreName() + ",none");
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
        }
        else {
            menuBox.setVisible(true);
            menuTopSeparator.setVisible(true);
            menuSideSeparator.setVisible(true);
            priceRangePane.setVisible(true);
        }
    }

    public void clickToProfile(MouseEvent mouseEvent) {
        try {
            FXRouter.goTo("profile", member.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
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

}
