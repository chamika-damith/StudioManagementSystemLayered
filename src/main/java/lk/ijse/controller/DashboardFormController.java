package lk.ijse.controller;

import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane DashboardRoot;
    public AnchorPane ChildRoot;
    @FXML
    public Label lblUserId;
    @FXML
    private Label lblUserName;

    public void initialize(){
        lblUserId.setText("001");
        try {
            SetUi("/view/Dashboard/DashboardWindow.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage= (Stage) DashboardRoot.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.centerOnScreen();
    }


    public void btnInventoryOnAction(ActionEvent actionEvent) throws IOException {
        SetUi("/view/Inventory/InventoryForm.fxml");
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        SetUi("/view/Customer/CustomerForm.fxml");

    }

    public void btnDasshboardOnAction(ActionEvent actionEvent) throws IOException {

        SetUi("/view/Dashboard/DashboardWindow.fxml");
    }
    public void SetUi(String location) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource(location));
        ChildRoot.getChildren().clear();
        ChildRoot.getChildren().add(parent);
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        SetUi("/view/Order/OrderForm.fxml");
    }

    public void btnBookingOnAction(ActionEvent actionEvent) throws IOException {
        SetUi("/view/Booking/BookingForm.fxml");

    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        SetUi("/view/Employee/EmployeeForm.fxml");
    }

    public void btnServiceOnAction(ActionEvent actionEvent) throws IOException {
        SetUi("/view/Service/ServiceForm.fxml");
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws IOException {
        SetUi("/view/Report/ReportForm.fxml");
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        SetUi("/view/Supplier/SupplierForm.fxml");
    }
}
