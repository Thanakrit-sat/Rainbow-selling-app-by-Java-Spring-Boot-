package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;

import java.io.IOException;
import ku.cs.FXRouter;
import ku.cs.shop.services.datasource.UserAccountSource;


public class RegisterController {
    private MemberAccount memberAccount;
    @FXML private Label signInLabel;
    @FXML private Label statusLabel;
    @FXML private TextField nameField;
    @FXML private TextField userNameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField cfPasswordField;
    private UserAccountSource dataSource;
    private MemberAccountList userList;


    @FXML void initialize(){
        statusLabel.setText("");
        dataSource = new UserAccountSource();
        userList = dataSource.readData();

    }

    @FXML   /*เมื่อเอาเมาส์ไปชี้ที่ sign in ตัวอักษรจะเป็นสีแดง*/
    public void onMouse(MouseEvent mouseEvent){
        signInLabel.setStyle("-fx-text-fill: #FF0000;");
    }

    @FXML   /*เมื่อเอาเมาส์ออกจาก sign in ตัวอักษรจะเป็นสีดพ*/
    public void exitMouse(MouseEvent mouseEvent){
        signInLabel.setStyle("-fx-text-fill: #000000;");
    }

    @FXML   /*คลิกที่ sign in เพื่อกลับไปหน้า login*/
    public void clickToSignIn(MouseEvent mouseEvent){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด login");
        }
    }

    @FXML   /*กด sign เพื่อสมัครสมาชิก */
    public void handleSignUpButton(ActionEvent actionEvent){
        String name = nameField.getText().trim();
        String username = userNameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPass = cfPasswordField.getText().trim();

        if (name.isBlank() || username.isBlank() || password.isBlank() || confirmPass.isBlank()){
            statusLabel.setText("Please complete the information");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
            return;
        }

        memberAccount = new MemberAccount("member", nameField.getText(), userNameField.getText(), passwordField.getText(), "images/profileDefault.png");

        if (password.equals(confirmPass) && userList.checkUsername(memberAccount)){
            dataSource.addData(memberAccount);
            /* method สำหรับเขียนไฟล์ dataSource ลง CSV */
            try {
                FXRouter.goTo("login");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า login ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด login");
            }
        }else{
            statusLabel.setText("Your password is incorrect" + "\n" + "or username has already been taken");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
        }
    }
}
