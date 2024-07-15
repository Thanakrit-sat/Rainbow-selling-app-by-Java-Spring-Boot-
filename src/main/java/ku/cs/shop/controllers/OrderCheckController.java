package ku.cs.shop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.FXRouter;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.Order;
import ku.cs.shop.models.OrderList;
import ku.cs.shop.services.comparator.OrderNewComparator;
import ku.cs.shop.services.datasource.OrderFileDataSource;
import ku.cs.shop.services.filterer.OrderOwnerConditionFilterer;

import java.io.IOException;

public class OrderCheckController {

    @FXML private ScrollPane scrollPane;
    @FXML private TableView<Order> orderTableView;
    @FXML private TableColumn<Order, String> buyerColumn;
    @FXML private TableColumn<Order, String> orderTimeColumn;
    @FXML private TableColumn<Order, String> productColumn;
    @FXML private TableColumn<Order, Integer> quantityColumn;
    @FXML private TableColumn<Order, Double> totalPriceColumn;
    @FXML private TableColumn<Order, String> statusColumn;
    @FXML private TableColumn<Order, String> trackNumColumn;
    @FXML private ImageView productImage;
    @FXML private VBox menuBox;
    @FXML private HBox checkOrderBox;
    @FXML private Label usernameLabel;

    private MemberAccount member;
    private Order order;
    private OrderList orders;
    private OrderFileDataSource orderFileDataSource;
    private ObservableList<Order> orderObservableList;


    @FXML
    public void initialize() {
        member = (MemberAccount) FXRouter.getData();
        usernameLabel.setText(member.getUsername());
        orderFileDataSource = new OrderFileDataSource();
        orders = orderFileDataSource.readData();
        getOrderStoreName();
        orders.sort(new OrderNewComparator());
        showOrderData();
        orderTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVlaue, newValue) -> {
            if (newValue != null) {
                showOrderImage(newValue);
            }
        });
    }

    private void showOrderImage(Order newValue) {
        order = newValue;
        productImage.setImage(new Image("file:" + order.getOrderProductImage(), true));
    }

    private void showOrderData() {
        orderObservableList = FXCollections.observableArrayList(orders.getOrders());
        orderTableView.setItems(orderObservableList);

        buyerColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("buyer"));
        productColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderProductName"));
        orderTimeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderTimeSell"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderAmount"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("orderTotalPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("statusOrder"));
        trackNumColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("trackNum"));
    }

    public void getOrderStoreName() {
        orders.setOrders(orders.search(new OrderOwnerConditionFilterer(member.getStoreName())));
    }

    @FXML
    public void clickToProfile(MouseEvent event) {
        try {
            FXRouter.goTo("profile", member.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
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
        }
        else {
            menuBox.setVisible(true);
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
    public void handleYourStore(MouseEvent event) {
        try {
            FXRouter.goTo("store", member.getUsername() + "," +member.getStoreName() + ",none");
        } catch (IOException e) {
            System.err.println("ไปหน้า store ไม่ได้");
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
