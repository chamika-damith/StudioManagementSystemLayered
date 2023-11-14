package lk.ijse.controller.Order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lk.ijse.controller.Customer.CustomerFormController;
import lk.ijse.controller.DashboardFormController;
import lk.ijse.controller.LoginFormController;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.OrderDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.ItemTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;
import lk.ijse.model.OrderModel;
import org.controlsfx.control.Notifications;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private CustomerModel customerModel = new CustomerModel();

    private ItemModel itemModel=new ItemModel();

    private ObservableList<CartTm> obList=FXCollections.observableArrayList();

    private DashboardFormController dashboardFormController;
    private CustomerFormController customerFormController;

    private OrderModel orderModel=new OrderModel();

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

            String itemId = (String) cmbItemId.getValue();
            int orderId = Integer.parseInt(lblOrderId.getText());
            String desc = lblItemDesc.getText();
            Date date = Date.valueOf(lblDate.getText());
            double price = Double.parseDouble(lblItemPrice.getText());
            int qty = Integer.parseInt(txtQty.getText());
            double totPrice = price*qty;
            Button btn = createButton();

            if (qty > 0) {

                setRemoveBtnAction(btn);
                btn.setCursor(Cursor.HAND);

                var cartTm = new CartTm(itemId, desc, date, totPrice, qty, btn);

                obList.add(cartTm);

                tblCart.setItems(obList);
                calculateTotal();
                tblCart.refresh();

                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Order Added to Cart");
                    notifications.title("Successfully Added");
                    notifications.hideAfter(Duration.seconds(4));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
                clearField();

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Quantity").show();
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
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                calculateTotal();
                tblCart.refresh();
            }
        });
    }

    public void clearField(){
        cmbItemId.getSelectionModel().clearSelection();
        cmbCustomerId.getSelectionModel().clearSelection();
        txtQty.clear();
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCart(actionEvent);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException {
        int orderId = Integer.parseInt(lblOrderId.getText());
        String desc = lblItemDesc.getText();
        Date date = Date.valueOf(lblDate.getText());
        int userId = Integer.parseInt(dashboardFormController.lblUserId.getText());
        int cusId = Integer.parseInt(customerFormController.txtId.getText());
        double total = Double.parseDouble(lblTotal.getText());
        Date returnDate = null;

        if (orderModel.isExists(orderId)){
            Image image=new Image("/Icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Orders is already added");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            var dto=new OrderDto(orderId,desc,date,returnDate,userId,cusId,total);
            orderModel.saveOrder(dto);
        }
    }
}
