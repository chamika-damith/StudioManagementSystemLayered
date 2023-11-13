package lk.ijse.controller.Order;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;

import java.sql.SQLException;
import java.util.List;

public class OrderFormController {
    public JFXComboBox cmbCustomerId;
    public JFXComboBox cmbItemId;
    private CustomerModel customerModel = new CustomerModel();

    private ItemModel itemModel=new ItemModel();

    public void initialize(){
        loadCustomerIds();
        loadItemId();
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
}
