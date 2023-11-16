package lk.ijse.controller.Booking;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.*;
import lk.ijse.model.BookingModel;
import lk.ijse.model.CustomerModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class BookingFormController {
    public JFXComboBox cmbCustomerID;
    public MFXDatePicker appDate;
    public JFXComboBox cmbEventType;
    public JFXTextField txtAddress;
    public JFXComboBox cmbPackage;
    public Label bookId;
    public JFXComboBox cmbEmpId;
    public AnchorPane BookinRoot;

    private BookingModel bookingModel=new BookingModel();

    private CustomerModel customerModel=new CustomerModel();


    public void initialize(){
        cmbEventType.setItems(FXCollections.observableArrayList(EventType.PHOTOGRAPHY,EventType.VIDEOGRAPHY,EventType.AUDIO_PRODUCTION,EventType.TV_SHOWS));
        generateNextBookId();
        loadCustomerIds();
        loadPackageIds();
        loadEmpIds();
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> idList = customerModel.getAllCustomer();

            for (CustomerDto dto : idList) {
                obList.add(String.valueOf(dto.getCusId()));
            }

            cmbCustomerID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        int cusId = Integer.parseInt(String.valueOf(cmbCustomerID.getValue()));
        Date date= Date.valueOf(appDate.getValue());
        String evenType = String.valueOf(cmbEventType.getValue());
        String address = txtAddress.getText();
        int pkg = Integer.parseInt(String.valueOf(cmbPackage.getValue()));
        int empId= Integer.parseInt(String.valueOf(cmbEmpId.getValue()));
        int bId= Integer.parseInt(bookId.getText());

        var dto=new BookingDto(bId,evenType,date,address,empId,cusId,pkg);
        try {
            boolean b = bookingModel.saveBookingDto(dto);
            if (b){
                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Booking Add Successfully");
                    notifications.title("Successfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextBookId() {

        try {
            int bookingId = bookingModel.generateNextBookId();
            bookId.setText(String.valueOf("00"+bookingId));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnPackageOnAction(ActionEvent actionEvent) throws IOException {
        BookinRoot.setEffect(new GaussianBlur());
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Booking/Package.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Package Form");

        stage.setOnHidden(event -> {
            BookinRoot.setEffect(null);
        });
        stage.show();
    }

    private void loadPackageIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PackageDto> idList = bookingModel.getAllPackage();

            for (PackageDto dto : idList) {
                obList.add(String.valueOf(dto.getPkgId()));
            }

            cmbPackage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEmpIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = bookingModel.getAllEmployee();

            for (EmployeeDto dto : idList) {
                obList.add(String.valueOf(dto.getEmpId()));
            }

            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
