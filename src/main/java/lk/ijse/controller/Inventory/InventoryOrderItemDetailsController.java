package lk.ijse.controller.Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.InventoryOrderItemDto;
import lk.ijse.dto.OrderItemDetailFormDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.InventoryOrderItemTm;
import lk.ijse.dto.tm.OrderItemTm;
import lk.ijse.model.InventoryOrderItemDetailModel;

import java.sql.SQLException;
import java.util.List;

public class InventoryOrderItemDetailsController {
    public AnchorPane inventoryOrderDetailViewRoot;
    public TableView tblOrderItems;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colName;
    public TableColumn colPrice;
    public TableColumn colCategory;
    public TableColumn colQty;

    private static int focusedIndex;
    public Label lblOrderId;
    public Label lblOrderDate;
    public Label lblTotal;
    private int total;

    ObservableList<InventoryOrderItemTm> obList;

    public void initialize(){
        setCellValueFactory();
        getAllValues();
    }


    private void getAllValues() {
        var model=new InventoryOrderItemDetailModel();

        obList= FXCollections.observableArrayList();


        try {
            List<InventoryOrderItemDto> dto=model.getAllValues(focusedIndex);

            for (InventoryOrderItemDto oDto : dto) {
                lblOrderId.setText(String.valueOf(oDto.getSupOrderId()));
                lblOrderDate.setText(String.valueOf(oDto.getOrderDate()));
                total= (int) ((oDto.getQty())*(oDto.getPrice()));
                lblTotal.setText(String.valueOf(total));
                obList.add(new InventoryOrderItemTm(
                        oDto.getItemId(),
                        oDto.getDescription(),
                        oDto.getName(),
                        oDto.getPrice(),
                        oDto.getCategory(),
                        oDto.getQty()
                ));
            }
            tblOrderItems.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getIndex(int index) {
        System.out.println(index);
        focusedIndex=index;
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }
}
