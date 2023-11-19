package lk.ijse.controller.Inventory;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dto.InventoryOrderDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.InventoryOrderTm;
import lk.ijse.model.*;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryOrderDetailFormController {
    public Label lblOrderId;
    public JFXComboBox cmbCategoey;
    public JFXComboBox cmbSupplierId;
    public JFXTextField txtQty;
    public JFXComboBox cmbItem;
    public TableView tblCart;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn Action;
    public Label lblOrderDate;
    public Label lblDescription;
    public Label lblUnitPrice;
    public Label lblSupplierName;
    public Label lblTotal;
    public AnchorPane InventoryRoot;

    private int itemID;

    private int qty;

    private InventoryOrderModel SupOrdermodel=new InventoryOrderModel();

    private ItemModel itemModel=new ItemModel();

    private SupplierModel supplierModel=new SupplierModel();

    private PlaceInventoryOrderModel placeOrder=new PlaceInventoryOrderModel();

    private ObservableList<InventoryOrderTm> obList=FXCollections.observableArrayList();

    public void initialize(){
        loadCategory();
        setDate();
        setCellValueFactory();
        generateNextOrderId();
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        Action.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }


    private void loadItemName(String name) {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDto = itemModel.getCategoryName(name);

            for (ItemDto dto : itemDto) {
                obList.add(dto.getName());
            }
            cmbItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupplier(String code) {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> supDto = supplierModel.getItemSupplier(code);

            for (SupplierDto dto : supDto) {
                obList.add(String.valueOf(dto.getId()));
            }
            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCategory() {

        cmbCategoey.setItems(FXCollections.observableArrayList("CAMERA", "LENS", "DRONE", "LIGHTS", "ACCESORIES"));

    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = (String) cmbItem.getValue();
        if (code==null || code.isEmpty()){
            throw new IllegalStateException("Item is empty");
        }else {
            try {
                ItemDto dto = itemModel.searchItemName(code);
                if (dto != null) {
                    lblDescription.setText(dto.getDescription());
                    lblUnitPrice.setText(String.valueOf(dto.getPrice()));
                    itemID=dto.getItemId();
                    cmbSupplierId.requestFocus();
                }else {
                    System.out.println("dto is null");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void cmbSuplierOnAction(ActionEvent event) {
        String code = String.valueOf(cmbSupplierId.getValue());
        if (code==null || code.isEmpty()){
            throw new IllegalStateException("Supplier is empty");
        }else {
            int id= Integer.parseInt(code);
            try {
                SupplierDto dto = supplierModel.searchSupplier(id);
                if (dto != null) {
                    lblSupplierName.setText(dto.getName());
                    txtQty.requestFocus();
                }else {
                    System.out.println("dto is null");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void CmbCategoryOnAction(ActionEvent event) {
        String code = (String) cmbCategoey.getValue();
        loadItemName(code);
        loadSupplier(code);

    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException {
        int id = Integer.parseInt(lblOrderId.getText());
        String description = lblDescription.getText();
        Date orderDate = Date.valueOf(lblOrderDate.getText());
        Date returnDate=null;
        String category = String.valueOf(cmbCategoey.getValue());
        String supplierId= String.valueOf(cmbSupplierId.getValue());
        int supId = Integer.parseInt(supplierId);
        int txtqty = Integer.parseInt(txtQty.getText());

        if (SupOrdermodel.isExists(id)){
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
        }else {
            List<InventoryOrderTm> cartTmList = new ArrayList<>();
            for (int i = 0; i < tblCart.getItems().size(); i++) {
                InventoryOrderTm cartTm = obList.get(i);
                cartTmList.add(cartTm);
            }

            var dto=new InventoryOrderDto(id,description,orderDate, null,category,supId,cartTmList,txtqty,qty);
            boolean isPlaceOrder = placeOrder.placeOrder(dto);
            if (isPlaceOrder){
                tblCart.getItems().clear();
                generateNextOrderId();
                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Inventory Order Successfully");
                    notifications.title("Successfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("Inventory order is not placed");
                Image image=new Image("/Icon/icons8-cancel-50.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Order Place Unsuccessfully");
                    notifications.title("Unsuccessfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void btnAddToCart(ActionEvent actionEvent) throws SQLException {
        String textDescription = lblDescription.getText();
        Date textDate = Date.valueOf(lblOrderDate.getText());
        double price = Double.parseDouble(lblUnitPrice.getText());
        qty= Integer.parseInt(txtQty.getText());
        double total=price*qty;

        int qtyIndex= Integer.parseInt(txtQty.getText());

        Button btn= createButton();

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblCart.getItems().size(); i++) {
                if (colItemId.getCellData(i).equals(itemID)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    return;
                }
            }
        }

        if (qty <= 0) {
            Image image=new Image("/Icon/icons8-cancel-50.png");
            try {
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Quantity is empty");
                notifications.title("Unsuccessful");
                notifications.hideAfter(Duration.seconds(4));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            setRemoveBtnAction(btn);
            btn.setCursor(Cursor.HAND);

            var cartTm=new InventoryOrderTm(itemID,textDescription,textDate,total,qty,btn);

            obList.add(cartTm);

            tblCart.setItems(obList);
            calculateTotal();
            tblCart.refresh();

            Image image=new Image("/Icon/iconsOk.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Stock Order Added to Cart");
                notifications.title("Successfully Added");
                notifications.hideAfter(Duration.seconds(4));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            InventoryRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblCart.getSelectionModel().getSelectedIndex();
                InventoryRoot.setEffect(null);
                obList.remove(focusedIndex);
                calculateTotal();
                tblCart.refresh();
            }
            InventoryRoot.setEffect(null);
        });
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


    public void btnInventoryDetails(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Inventory/InventoryForm.fxml"));
        InventoryRoot.getChildren().clear();
        InventoryRoot.getChildren().add(parent);
    }

    public void btnInventoryOrder(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("/view/Inventory/InventoryOrderDetail.fxml"));
        InventoryRoot.getChildren().clear();
        InventoryRoot.getChildren().add(parent);
    }

    public void btnViewOrderDetail(ActionEvent actionEvent) {
    }

    private void generateNextOrderId() {
        try {
            int orderID = InventoryOrderModel.generateNextOrderId();
            lblOrderId.setText(String.valueOf("00"+orderID));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
