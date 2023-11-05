package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;

public class EmployeeFormController {
    public JFXComboBox<String> comboBox;

    public void initialize(){
        comboBox.setItems(FXCollections.observableArrayList("Admin","cashier","Manager","worker"));
    }
}
