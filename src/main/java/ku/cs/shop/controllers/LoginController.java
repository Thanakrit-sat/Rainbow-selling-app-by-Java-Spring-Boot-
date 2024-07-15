package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.services.datasource.UserAccountSource;

import java.io.IOException;
import java.time.LocalDateTime;



public class LoginController {

    @FXML private Label singUpLabel;
    @FXML private Label statusLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private UserAccountSource dataSource;
    private MemberAccountList userList;
    private MemberAccount member;

    @FXML void initialize(){
        statusLabel.setText("");
        dataSource = new UserAccountSource();
        userList = dataSource.readData();

    }

    @FXML
    public void UseLogin(ActionEvent actionEvent){
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isBlank() || password.isBlank()) {
            statusLabel.setText("Please complete the information");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
            return;
        }
        member = userList.getUserAccountByUsername(usernameField.getText());
        if (member == null || (!member.getPassword().equals(passwordField.getText()))) {
            statusLabel.setText("Incorrect username or password");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
            return;
        }

        if (member.getAccess().equals("deny")){
            member.setBanAccessCounting(member.getBanAccessCounting()+1);
            statusLabel.setText("Your access is denied");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
            return;
        }

        // check all correct
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ dev
            member.setTimeLogin(LocalDateTime.now().withNano(0) + "");
            dataSource.writeData(userList);
            // ------------------------- มาลบด้วย --------------------
            //recordDataSource.writeData(member);
            FXRouter.goTo("market", member);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า market ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด market");
        }
    }

    @FXML
    public void onMouse(MouseEvent mouseEvent){
        singUpLabel.setStyle("-fx-text-fill: #FF0000;");
    }

    @FXML
    public void exitMouse(MouseEvent mouseEvent){
        singUpLabel.setStyle("-fx-text-fill: #000000;");
    }

    @FXML
    public void clickToRegister(MouseEvent mouseEvent){
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ dev
            FXRouter.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด register");
        }
    }
}
