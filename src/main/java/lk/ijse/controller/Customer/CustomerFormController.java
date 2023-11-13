package lk.ijse.controller.Customer;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    public AnchorPane CustomerRoot;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtMobile;
    public Label lblCus;
    public Label cusId;
    private CustomerModel model=new CustomerModel();

    public void initialize(){
        setValueLable();
        generateNextCusId();
    }


    private void generateNextCusId() {
        try {
            int cusid = CustomerModel.generateNextCusId();
            cusId.setText(String.valueOf("00"+cusid));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String mobile = txtMobile.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        CustomerDto dto=new CustomerDto(id,name,mobile,email,address);

        try {
            boolean b = model.saveCustomer(dto);
            if (b) {
                setValueLable();
                clearField();
                generateNextCusId();
                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Customer Saved Successfully");
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

    public void setValueLable(){
        try {

            int count =0;
            List<CustomerDto> allItems = model.getAllItems();

            for (CustomerDto dto : allItems){
                count+=1;
            }

            lblCus.setText(String.valueOf(count));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void clearField(){
        txtId.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtName.clear();
        txtMobile.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String mobile = txtMobile.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        CustomerDto dto=new CustomerDto(id,name,mobile,email,address);

        try {
            boolean b = model.updateCustomer(dto);
            if (b) {
                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Customer Update Successfully");
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

    public void txtSearchOnAction(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtId.getText());

        try {
            CustomerDto dto = model.searchCustomer(id);
            if (dto != null){
                txtName.setText(dto.getName());
                txtMobile.setText(dto.getMobile());
                txtEmail.setText(dto.getEmail());
                txtAddress.setText(dto.getAddress());

                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Customer Search Successfully");
                    notifications.title("Successfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                Image image=new Image("/Icon/icons8-cancel-50.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Customer id does not exist");
                    notifications.title("Not Successfully");
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
}
