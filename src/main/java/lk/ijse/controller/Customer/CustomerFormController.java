package lk.ijse.controller.Customer;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.ItemTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane CustomerRoot;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtMobile;
    public Label lblCus;
    public Label cusId;
    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colMobile;
    public TableColumn colAction;
    private CustomerModel model=new CustomerModel();

    private ObservableList<CustomerTm> obList;

    public void initialize(){
        setValueLable();
        generateNextCusId();
        setCellValue();
        getAllCustomer();
    }

    private void setCellValue() {
        colId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    private void getAllCustomer() {
        var model=new CustomerModel();

        obList= FXCollections.observableArrayList();

        try {
            List<CustomerDto> allCustomer = model.getAllCustomer();

            for (CustomerDto dto : allCustomer){
                Button button = createButton();
                obList.add(new CustomerTm(
                        dto.getCusId(),
                        dto.getName(),
                        dto.getMobile(),
                        dto.getEmail(),
                        dto.getAddress(),
                        button
                ));

            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Button createButton(){
        Button btn=new Button("Delete");
        btn.getStyleClass().add("ActionBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setDeleteBtnAction(btn);
        return btn;
    }

    private void setDeleteBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblCustomer.getSelectionModel().getSelectedIndex();
                CustomerTm selectedCustomer = (CustomerTm) tblCustomer.getSelectionModel().getSelectedItem();

                if (selectedCustomer != null) {
                    int cusId = selectedCustomer.getCusId();
                    try {
                        boolean b = model.deleteCustomer(cusId);
                        if (b) {

                            Image image=new Image("/Icon/iconsDelete.png");
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Customer Delete Successfully");
                            notifications.title("Successfully");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();

                            System.out.println("delete selected");
                            obList.remove(focusedIndex);
                            getAllCustomer();
                            setValueLable();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
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
                getAllCustomer();
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
            List<CustomerDto> allItems = model.getAllCustomer();

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
                getAllCustomer();
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
