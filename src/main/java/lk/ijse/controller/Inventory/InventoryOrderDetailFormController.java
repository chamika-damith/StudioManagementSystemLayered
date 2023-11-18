package lk.ijse.controller.Inventory;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.SupplierOrderDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.InventoryOrderTm;
import lk.ijse.model.ItemModel;
import lk.ijse.model.SupplierModel;
import lk.ijse.model.SupplierOrderModel;
import org.controlsfx.control.Notifications;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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

    private SupplierOrderModel SupOrdermodel=new SupplierOrderModel();

    private ItemModel itemModel=new ItemModel();

    private SupplierModel supplierModel=new SupplierModel();

    private ObservableList<InventoryOrderTm> obList=FXCollections.observableArrayList();

    public void initialize(){
        loadCategory();
        setDate();
        setCellValueFactory();
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

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
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

    public void btnOrderTableOnAction(ActionEvent actionEvent) {
    }

    public void btnorderFormOnAction(ActionEvent actionEvent) {
    }
}
