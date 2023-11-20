package lk.ijse.controller.Customer;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.barCode.RegexPattern;
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

import static io.github.palexdev.materialfx.controls.MFXTextField.DEFAULT_TEXT_COLOR;

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
    public JFXTextField txtCostSearchTable;
    private CustomerModel model=new CustomerModel();

    private ObservableList<CustomerTm> obList;

    public static int id;

    public void initialize(){
        generateNextCusId();
        setCellValue();
        getAllCustomer();
        searchTable();
    }

    private void setCellValue() {
        colId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
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
            CustomerRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            CustomerRoot.setEffect(null);

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
                            searchTable();
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
            txtId.setText("00"+cusid);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {


        if (isEmptyCheck()){
            Image image=new Image("/Icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Value is empty! Please enter all values");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String mobile = txtMobile.getText();
            String email = txtEmail.getText();
            String address = txtAddress.getText();

            try {
                if (model.isExists(id)) {
                    clearField();
                    Image image=new Image("/Icon/icons8-cancel-50.png");
                    try {
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Customer is already registered");
                        notifications.title("Warning");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {

                    if (checkValidate()){

                        nullTextFieldColor();

                        CustomerDto dto=new CustomerDto(id,name,mobile,email,address);

                        try {
                            boolean b = model.saveCustomer(dto);
                            if (b) {
                                clearField();
                                generateNextCusId();
                                getAllCustomer();
                                searchTable();
                                nullTextFieldColor();
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
                    }else {
                        Image image=new Image("/Icon/icons8-cancel-50.png");
                        try {
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Invalid input..Please enter a valid input ");
                            notifications.title("Error");
                            notifications.hideAfter(Duration.seconds(4));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

        if (isEmptyCheck()){
            Image image=new Image("/Icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Value is empty! Please enter all values");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
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
                    searchTable();
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

    public void searchTable() {
        FilteredList<CustomerTm> filteredData = new FilteredList<>(obList, b -> true);

        txtCostSearchTable.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customerTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String cusId = String.valueOf(customerTm.getCusId());
                String name = customerTm.getName().toLowerCase();

                return cusId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter);
            });
        });

        SortedList<CustomerTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCustomer.comparatorProperty());
        tblCustomer.setItems(sortedData);
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void txtAddressOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void txtEmailOnAction(ActionEvent actionEvent) {
        txtMobile.requestFocus();
    }

    public boolean isEmptyCheck() {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtEmail.getText().isEmpty()
        || txtMobile.getText().isEmpty()) {
            System.out.println("Customer field is empty");
            return true;
        }else {
            return false;
        }
    }

    public boolean checkValidate(){
        if (!(RegexPattern.getNamePattern().matcher(txtName.getText()).matches())) {
            txtName.requestFocus();
            txtName.setFocusColor(Color.RED);
            return false;
        }

        if (!(RegexPattern.getAddressPattern().matcher(txtAddress.getText()).matches())){
            txtAddress.requestFocus();
            txtAddress.setFocusColor(Color.RED);
            return false;
        }

        if (!(RegexPattern.getEmailPattern().matcher(txtEmail.getText()).matches())){
            txtEmail.requestFocus();
            txtEmail.setFocusColor(Color.RED);
            return false;
        }

        if (!(RegexPattern.getMobilePattern().matcher(txtMobile.getText()).matches())){
            txtMobile.requestFocus();
            txtMobile.setFocusColor(Color.RED);
            return false;
        }

        return true;
    }

    private void nullTextFieldColor() {
        txtId.setFocusColor(Color.web("#0040ff"));
        txtName.setFocusColor(Color.web("#0040ff"));
        txtMobile.setFocusColor(Color.web("#0040ff"));
        txtEmail.setFocusColor(Color.web("#0040ff"));
        txtAddress.setFocusColor(Color.web("#0040ff"));
    }
}
