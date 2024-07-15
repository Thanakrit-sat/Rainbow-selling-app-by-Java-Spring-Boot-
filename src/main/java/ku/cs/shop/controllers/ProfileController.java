package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ku.cs.FXRouter;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.services.datasource.UserAccountSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class ProfileController {
    @FXML private Label nameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ImageView userImage;
    @FXML private Button uploadButton;
    @FXML private VBox adminBox;
    @FXML private VBox menuBox;
    @FXML private Separator menuSideSeparator;
    @FXML private Separator menuTopSeparator;
    @FXML private Label usernameLabelProfile;
    @FXML private ScrollPane scrollPane;


    private String memberUsername;
    private MemberAccount member;
    private MemberAccountList users;
    private UserAccountSource dataSource;

    public void initialize() {
        memberUsername = (String) FXRouter.getData();
        dataSource = new UserAccountSource();
        users = dataSource.readData();
        member = users.getUserAccountByUsername(memberUsername);
        nameLabel.setText(member.getName());
        usernameLabel.setText(member.getUsername());
        usernameLabelProfile.setText(member.getUsername());
        //userImage.setImage(new Image(getClass().getResource(member.getImagePath()).toExternalForm()));
        userImage.setImage(new Image("file:"+member.getImagePath(), true));
        statusLabel.setText("");
        showAdminBox();
    }


    @FXML
    public void confirmChangePassword() {
        if (currentPasswordField.getText().isBlank() || newPasswordField.getText().isBlank() || confirmPasswordField.getText().isBlank()) {
            statusLabel.setText("Please complete the information");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
            return;
        }

        if (!member.checkPassword(currentPasswordField.getText()) || !newPasswordField.getText().equals(confirmPasswordField.getText())) {
            statusLabel.setText("Password is incorrect");
            statusLabel.setStyle("-fx-text-fill: #A31F1F ;");
            return;
        }

        if (member.checkPassword(currentPasswordField.getText()) && newPasswordField.getText().equals(confirmPasswordField.getText())) {
            statusLabel.setText("Complete");
            statusLabel.setStyle("-fx-text-fill: #2ECC71 ;");
            member.setPassword(confirmPasswordField.getText());
            // update csv-useraccount
            dataSource.writeData(users);
        }
    }

    @FXML
    public void handleUploadButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG","*.png", "*.jpg", "*.jpeg" ));

        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());

        if (file != null) {
            try {
                File destDir = new File("images");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_"+ member.getUsername() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
                );

                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // set image
                userImage.setImage(new Image(target.toUri().toString()));
                // delete picture path
                deleteFilePicture(member.getImagePath());
                member.setImagePath(destDir + "/" + filename);
                dataSource.writeData(users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void deleteFilePicture(String filename) {
        if (filename.equals("images/profileDefault.png")) {
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

    public void showAdminBox() {
        if (member.getType().equals("admin")) {
            adminBox.setVisible(true);
            adminBox.setDisable(false);
        }
        else {
            adminBox.setVisible(false);
        }
    }

    @FXML
    public void handleAdminBox(MouseEvent event) {
        if (member.getType().equals("admin")) {
            try {
                FXRouter.goTo("admin", member.getUsername());
            } catch (IOException e) {
                System.err.println("ไปที่หน้า admin ไม่ได้");
            }
        }
    }


    // MENU -------------------

    @FXML
    public void handleMenuButton(MouseEvent mouseEvent){
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
            else FXRouter.goTo("store",member.getUsername() + "," + member.getStoreName() + ",none");
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
    public void handleMarketMenu(MouseEvent event) {
        try {
            FXRouter.goTo("market");
        } catch (IOException e) {
            System.err.println("ไปหน้า market ไม่ได้");
        }
    }
}
