package lk.ijse.controller.Order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.controller.Customer.CustomerFormController;
import lk.ijse.controller.DashboardFormController;
import lk.ijse.controller.LoginFormController;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.ItemTm;
import lk.ijse.dto.tm.OrderItemTm;
import lk.ijse.model.*;
import lk.ijse.regex.RegexPattern;
import lk.ijse.smtp.MailSend;
import org.controlsfx.control.Notifications;

import javax.naming.ldap.PagedResultsControl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderFormController {
    public JFXComboBox cmbCustomerId;
    public JFXComboBox cmbItemId;
    public Label lblOrderId;
    public Label lblCusName;
    public Label lblItemQty;
    public Label lblItemPrice;
    public Label lblItemDesc;
    public Label lblDate;
    public TableView<CartTm> tblCart;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn Action;
    public JFXTextField txtQty;
    public Label lblTotal;
    public AnchorPane OrderCartRoot;

    private String cusName;
    private int saveQty;

    private int lblQty;
    private int textQty;
    private int qty;
    private int textItemId;

    private String emailAddress;
    private CustomerModel customerModel = new CustomerModel();

    private ItemModel itemModel=new ItemModel();

    private ObservableList<CartTm> obList=FXCollections.observableArrayList();

    private OrderModel orderModel=new OrderModel();
    private PlaceOrderModel placeOrderModel=new PlaceOrderModel();

    private OrderItemDetailFormModel orderItemDetailFormModel=new OrderItemDetailFormModel();


    public void initialize(){
        loadCustomerIds();
        loadItemId();
        generateNextOrderId();
        setDate();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        Action.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String id = (String) cmbCustomerId.getValue();

        if (id != null && !id.isEmpty()) {
            try {
                CustomerDto customerDto = customerModel.searchCustomer(Integer.parseInt(id));
                lblCusName.setText(customerDto.getName());
                cusName=customerDto.getName();
                emailAddress=customerDto.getEmail();
                cmbItemId.requestFocus();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            System.out.println("Customer ID is null or empty.");
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = (String) cmbItemId.getValue();

        try {
            ItemDto dto = itemModel.searchItems(code);
            if (dto != null) {
                lblItemDesc.setText(dto.getDescription());
                lblItemPrice.setText(String.valueOf(dto.getPrice()));
                lblItemQty.setText(String.valueOf(dto.getQty()));
                textItemId=dto.getItemId();
                txtQty.requestFocus();
            }else {
                System.out.println("dto is null");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextOrderId() {
        try {
            int orderID = OrderModel.generateNextOrderId();
            lblOrderId.setText(String.valueOf("00"+orderID));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> idList = customerModel.getAllCustomer();

            for (CustomerDto dto : idList) {
                obList.add(String.valueOf(dto.getCusId()));
            }

            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDto = itemModel.getAllItems();

            for (ItemDto dto : itemDto) {
                obList.add(String.valueOf(dto.getItemId()));
            }
            cmbItemId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddToCart(ActionEvent actionEvent) {

        if (isEmptyCheck()){
            Image image=new Image("/Icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Value is empty! Please enter all values");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {

            if (checkValidate()){

                String itemId = (String) cmbItemId.getValue();
                int orderId = Integer.parseInt(lblOrderId.getText());
                String desc = lblItemDesc.getText();
                Date date = Date.valueOf(lblDate.getText());
                double price = Double.parseDouble(lblItemPrice.getText());
                qty = Integer.parseInt(txtQty.getText());
                double totPrice = price * qty;
                Button btn = createButton();

                lblQty = Integer.parseInt(lblItemQty.getText());
                textQty = Integer.parseInt(txtQty.getText());

                if (!obList.isEmpty()) {
                    for (int i = 0; i < tblCart.getItems().size(); i++) {
                        if (colItemId.getCellData(i).equals(itemId)) {
                            int col_qty = (int) colQty.getCellData(i);
                            qty += col_qty;
                            return;
                        }
                    }
                }

                if (lblQty > 0 & textQty < lblQty) {

                    saveQty = lblQty - textQty;
                    lblItemQty.setText(String.valueOf(saveQty));

                    setRemoveBtnAction(btn);
                    btn.setCursor(Cursor.HAND);

                    var cartTm = new CartTm(itemId, desc, date, totPrice, qty, btn);

                    obList.add(cartTm);

                    tblCart.setItems(obList);
                    calculateTotal();
                    tblCart.refresh();

                    Image image = new Image("/Icon/iconsOk.png");
                    try {
                        Notifications notifications = Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Order Added to Cart");
                        notifications.title("Successfully Added");
                        notifications.hideAfter(Duration.seconds(4));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    OrderCartRoot.setEffect(new GaussianBlur());
                    Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "Stock Count is Low.Do you want to order this item in stock ?", ButtonType.OK, ButtonType.NO).showAndWait();
                    if (buttonType.orElse(ButtonType.NO) == ButtonType.OK) {
                        OrderCartRoot.setEffect(null);
                        Parent parent = null;
                        try {
                            parent = FXMLLoader.load(getClass().getResource("/view/Inventory/InventoryOrderDetail.fxml"));
                            OrderCartRoot.getChildren().clear();
                            OrderCartRoot.getChildren().add(parent);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    OrderCartRoot.setEffect(null);
                }
            }else {
                Image image=new Image("/Icon/icons8-cancel-50.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Invalid input..Please enter a valid input ");
                    notifications.title("Error");
                    notifications.hideAfter(Duration.seconds(4));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        }
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colPrice.getCellData(i);
        }
        lblTotal.setText(String.valueOf(total));
    }

    public Button createButton(){
        Button btn=new Button("Remove");
        btn.getStyleClass().add("ActionBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        return btn;
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            OrderCartRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblCart.getSelectionModel().getSelectedIndex();
                OrderCartRoot.setEffect(null);
                obList.remove(focusedIndex);
                calculateTotal();
                tblCart.refresh();
            }
            OrderCartRoot.setEffect(null);
        });
    }


    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCart(actionEvent);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException {

        if (tblCart.getItems().isEmpty()) {

                Image image=new Image("/Icon/icons8-cancel-50.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Cart is empty! Please add cart to items");
                    notifications.title("Warning");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
        }    else {

            int orderId = Integer.parseInt(lblOrderId.getText());
            Date date = Date.valueOf(lblDate.getText());
            int userId = 001;
            String cusId = (String) cmbCustomerId.getValue();
            int customerId = Integer.parseInt(cusId);
            double total = Double.parseDouble(lblTotal.getText());
            Date returnDate = null;

            if (orderModel.isExists(orderId)) {
                Image image = new Image("/Icon/icons8-cancel-50.png");
                try {
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Orders is already added");
                    notifications.title("Warning");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                List<CartTm> cartTmList = new ArrayList<>();
                for (int i = 0; i < tblCart.getItems().size(); i++) {
                    CartTm cartTm = obList.get(i);
                    cartTmList.add(cartTm);
                }


                var orderDto = new OrderDto(orderId, date, returnDate, userId, customerId, total, saveQty, qty, cartTmList);
                boolean b = placeOrderModel.placeOrder(orderDto);
                if (b) {
                    tblCart.getItems().clear();

                    List<OrderItemDetailFormDto> allValues = orderItemDetailFormModel.getAllValues(orderId);


                    String subject = "Your Purchase Order Confirmation - Order #00[" + orderId + "]";

                    String text = "Dear " + cusName + ",\n" +
                            "\n" +
                            "Thank you for choosing FOCUS Photography Studio! We are delighted to confirm your recent purchase. Here are the details of your order:\n\n" +
                            "Order Number:  00" + orderId + "\n" +
                            "Date of Purchase:  " + date + "\n\n";


                    for (OrderItemDetailFormDto product : allValues) {
                        text += "\nProduct: " + product.getName() +
                                "\n   - Quantity: " + product.getQty() +
                                "\n   - Description: " + product.getDescription() +
                                "\n   - Total: " + product.getPrice() + "\n";

                    }


                    text += "\nIf you have any questions or concerns regarding your order, please feel free to contact our customer support team at Chamikadamith9@gmail.com or 0785765111.\n\n" +
                            "\nThank you for choosing FOCUS Photography Studio! We appreciate your business.\n\n" +
                            "\nBest regards,\nFOCUS Photography Studio Team";


                    MailSend.sendOrderConformMail(emailAddress, subject, text);

                    generateNextOrderId();
                    Image image = new Image("/Icon/iconsOk.png");
                    try {
                        Notifications notifications = Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Order Place Successfully");
                        notifications.title("Successfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("order is not placed");
                    Image image = new Image("/Icon/icons8-cancel-50.png");
                    try {
                        Notifications notifications = Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Order Place Unsuccessfully");
                        notifications.title("Unsuccessfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public void btnOrderTableOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Order/ViewTableForm.fxml"));
        OrderCartRoot.getChildren().clear();
        OrderCartRoot.getChildren().add(parent);
    }

    public void btnorderFormOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Order/OrderForm.fxml"));
        OrderCartRoot.getChildren().clear();
        OrderCartRoot.getChildren().add(parent);
    }

    private boolean isEmptyCheck(){
        if (cmbCustomerId.getValue() == null || cmbItemId.getValue() == null || txtQty.getText().isEmpty()){
            System.out.println("order value is null or empty");
            return true;
        }
        return false;
    }

    private boolean checkValidate(){
        if (!(RegexPattern.getIntPattern().matcher(txtQty.getText()).matches())){
            txtQty.requestFocus();
            txtQty.setFocusColor(Color.RED);
            return false;
        }
        return true;
    }
    private void nullTextFieldColor() {
        txtQty.setFocusColor(Color.web("#0040ff"));
    }
}
