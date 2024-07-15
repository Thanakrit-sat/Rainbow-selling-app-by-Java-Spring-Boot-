package ku.cs.shop.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.services.datasource.UserAccountSource;
import ku.cs.shop.services.comparator.UserLastLoginComparator;

import java.io.IOException;

public class AdminController {
    @FXML private TableView<MemberAccount> memberTableView;

    @FXML private Label nameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label storeNameLabel;
    @FXML private Label lastLoginTimeLabel;
    @FXML private Label accessLabel;
    @FXML private Label countingLabel;
    @FXML private ImageView userImage;

    @FXML
    private TableColumn<MemberAccount, String> usernameColumn;
    @FXML
    private TableColumn<MemberAccount, String> loginTimeColumn;

    private UserAccountSource dataSource;
    private MemberAccountList userList;
    private MemberAccount selectedMember;
    private ObservableList<MemberAccount> memberAccountObservableList;
    private MemberAccount admin;


    @FXML
    public void initialize() {
        dataSource = new UserAccountSource();
        userList = dataSource.readData();

        String username = (String) FXRouter.getData();
        admin = userList.getUserAccountByUsername(username);
        userList.sort(new UserLastLoginComparator());
        clearSelected();
        showMemberAccountData();
        memberTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedMember(newValue);
            }
        });
    }

    @FXML
    public void handleBackButton(MouseEvent event){
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ market
            FXRouter.goTo("profile");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด profile");
        }

    }

    @FXML
    public void handleCheckReport(MouseEvent mouseEvent) {
        try {
            FXRouter.goTo("reportinfo", admin);
        } catch (IOException e) {
            System.err.println("ไปหน้า check report ไม่ได้");
        }
    }

    public void showMemberAccountData() {
        if (admin.getType().equals("admin")) {
            memberAccountObservableList = FXCollections.observableArrayList(userList.getAllUsers());
            memberTableView.setItems(memberAccountObservableList);
            usernameColumn.setCellValueFactory(new PropertyValueFactory<MemberAccount, String>("username"));
            loginTimeColumn.setCellValueFactory(new PropertyValueFactory<MemberAccount, String>("timeLogin"));
        }
    }

    public void showSelectedMember(MemberAccount member) {
        selectedMember = member;
        nameLabel.setText(selectedMember.getName());
        usernameLabel.setText(selectedMember.getUsername());
        storeNameLabel.setText(selectedMember.getStoreName());
        accessLabel.setText(selectedMember.getAccess());
        if (selectedMember.getAccess().equals("allow")){
            accessLabel.setStyle("-fx-text-fill: #00ccaa;");
        }
        else{
            accessLabel.setStyle("-fx-text-fill: #ff546b;");
        }
        countingLabel.setText(String.valueOf(selectedMember.getBanAccessCounting()));
        userImage.setImage(new Image("file:"+member.getImagePath(), true));
        lastLoginTimeLabel.setText(member.getTimeLogin());
    }

    public void clearSelected() {
        nameLabel.setText("");
        usernameLabel.setText("");
        storeNameLabel.setText("");
        lastLoginTimeLabel.setText("");
        accessLabel.setText("");
        countingLabel.setText("");
    }

    public void handleSwitchAccessibility(){
        if (!selectedMember.getType().equals("admin")) {
            if (selectedMember.getAccess().equals("allow")) {
                selectedMember.setAccess("deny");
                accessLabel.setText("deny");
                accessLabel.setStyle("-fx-text-fill: #ff546b;");
            } else if (selectedMember.getAccess().equals("deny")) {
                selectedMember.setAccess("allow");
                selectedMember.setBanAccessCounting(0);
                accessLabel.setText("allow");
                accessLabel.setStyle("-fx-text-fill: #00ccaa;");
                countingLabel.setText(String.valueOf(selectedMember.getBanAccessCounting()));
            }
            dataSource.writeData(userList);
        } else{
            accessLabel.setText("Unable to change accessibility");
            accessLabel.setStyle("-fx-text-fill: #ff546b;");
        }
    }
}
