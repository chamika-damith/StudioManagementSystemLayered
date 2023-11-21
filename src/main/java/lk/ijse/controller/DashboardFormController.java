package lk.ijse.controller;

import com.mysql.cj.log.Log;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {
    public AnchorPane DashboardRoot;
    public AnchorPane ChildRoot;
    @FXML
    public Label lblUserId;
    public TextField txtSearchBar;
    public Label lblDateTime;
    @FXML
    private Label lblUserName;

    public void initialize(){
        lblUserId.setText("001");
        DateTime();
        search();
        try {
            SetUi("/view/Dashboard/DashboardWindow.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void DateTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            lblDateTime.setText(date.format(DateTimeFormatter.ISO_DATE) + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void search(){
        String[] suggestionList = {"Inventory", "Customer", "Booking", "Orders", "Supplier","Package","Employee","Report"};
        TextFields.bindAutoCompletion(txtSearchBar, suggestionList);
    }

    public void searchOnAction(ActionEvent actionEvent) throws IOException {
        String text = txtSearchBar.getText();
        switch (text){
            case "Inventory":
                btnInventoryOnAction(actionEvent);
                break;
            case "Customer" :
                btnCustomerOnAction(actionEvent);
                break;
            case "Booking":
                btnBookingOnAction(actionEvent);
                break;
            case "Orders":
                btnOrderOnAction(actionEvent);
                break;
            case "Supplier":
                btnSupplierOnAction(actionEvent);
                break;
            case "Package":
                btnServiceOnAction(actionEvent);
            case "Employee":
                btnEmployeeOnAction(actionEvent);
                break;
            case "Report":
                btnReportOnAction(actionEvent);
                break;
            default:
                btnDasshboardOnAction(actionEvent);
                break;
        }
    }

    public void txtSearchBarOnAction(ActionEvent actionEvent) throws IOException {
        String text = txtSearchBar.getText();
        switch (text){
            case "Inventory":
                btnInventoryOnAction(actionEvent);
                break;
            case "Customer" :
                btnCustomerOnAction(actionEvent);
                break;
            case "Booking":
                btnBookingOnAction(actionEvent);
                break;
            case "Orders":
                btnOrderOnAction(actionEvent);
                break;
            case "Supplier":
                btnSupplierOnAction(actionEvent);
                break;
            case "Package":
                btnServiceOnAction(actionEvent);
            case "Employee":
                btnEmployeeOnAction(actionEvent);
                break;
            case "Report":
                btnReportOnAction(actionEvent);
                break;
            default:
                btnDasshboardOnAction(actionEvent);
                break;
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
