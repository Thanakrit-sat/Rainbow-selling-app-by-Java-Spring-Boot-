package ku.cs.shop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.models.ReportList;
import ku.cs.shop.models.Reporter;
import ku.cs.shop.services.datasource.ReportFileDataSource;
import ku.cs.shop.services.datasource.UserAccountSource;

import java.io.IOException;

public class ReportInfoController {


    @FXML private TableView<Reporter> reportTableView;
    @FXML private TableColumn<Reporter, String> reporterColumn;
    @FXML private TableColumn<Reporter, String> problemTypeColumn;
    @FXML private TableColumn<Reporter, String> receiverColumn;
    @FXML private TableColumn<Reporter, String> productNameColumn;
    @FXML private TextArea detailTextArea;
    @FXML private Label reporterNameLabel;
    @FXML private Label productNameLabel;
    @FXML private Label problemTypeLabel;
    @FXML private Label storeNameLabel;
    @FXML private Label storeOwnerNameLabel;
    @FXML private Label accessLabel;

    private UserAccountSource dataSource;
    private MemberAccountList userList;
    private ReportFileDataSource reportFileDataSource;
    private ReportList reportList;
    private Reporter selectedReporter;
    private MemberAccount admin;
    private ObservableList<Reporter> reporterObservableList;


    @FXML
    public void initialize() {
        admin = (MemberAccount) FXRouter.getData();
        reportFileDataSource = new ReportFileDataSource();
        dataSource = new UserAccountSource();
        userList = dataSource.readData();
        reportList = reportFileDataSource.readData();
        clearSelected();
        showReportData();

        reportTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedReporter(newValue);
            }
        });
    }

    private void showSelectedReporter(Reporter newValue) {
        selectedReporter = newValue;
        reporterNameLabel.setText(selectedReporter.getReporter());
        problemTypeLabel.setText(selectedReporter.getTitle());
        detailTextArea.setText(selectedReporter.getText());
        storeNameLabel.setText(selectedReporter.getReceiver());
        storeOwnerNameLabel.setText(userList.getUsernameByStoreName(selectedReporter.getReceiver()));
        productNameLabel.setText(selectedReporter.getProductName());
    }

    private void showReportData() {
        reporterObservableList = FXCollections.observableArrayList(reportList.getReportInfo());
        reportTableView.setItems(reporterObservableList);

        reporterColumn.setCellValueFactory(new PropertyValueFactory<Reporter, String>("reporter"));
        problemTypeColumn.setCellValueFactory(new PropertyValueFactory<Reporter, String>("title"));
        receiverColumn.setCellValueFactory(new PropertyValueFactory<Reporter, String>("receiver"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Reporter, String>("productName"));
    }


    private void clearSelected() {
        reporterNameLabel.setText("");
        detailTextArea.setText("");
        problemTypeLabel.setText("");
        storeNameLabel.setText("");
        storeOwnerNameLabel.setText("");
        productNameLabel.setText("");
    }

    @FXML public void handleBackButton(MouseEvent event) {
        try {
            FXRouter.goTo("admin", admin.getUsername());
        } catch (IOException e) {
            System.err.println("ไปหน้า admin ไม่ได้");
        }
    }

}
