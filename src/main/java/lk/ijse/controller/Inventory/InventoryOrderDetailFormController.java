package lk.ijse.controller.Inventory;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.SupplierOrderDto;
import lk.ijse.model.ItemModel;
import lk.ijse.model.SupplierOrderModel;

import java.sql.SQLException;
import java.util.List;

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

    private SupplierOrderModel SupOrdermodel=new SupplierOrderModel();

    private ItemModel itemModel=new ItemModel();

    public void initialize(){
        loadItemName();
    }


    private void loadItemName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDto = itemModel.getAllItems();

            for (ItemDto dto : itemDto) {
                obList.add(dto.getName());
            }
            cmbItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = (String) cmbItem.getValue();

        try {
            ItemDto dto = itemModel.searchItemName(code);
            if (dto != null) {
                lblDescription.setText(dto.getDescription());
                lblUnitPrice.setText(String.valueOf(dto.getPrice()));
                cmbSupplierId.requestFocus();
            }else {
                System.out.println("dto is null");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnAddToCart(ActionEvent actionEvent) {

    }

    public void btnOrderTableOnAction(ActionEvent actionEvent) {
    }

    public void btnorderFormOnAction(ActionEvent actionEvent) {
    }
}
