package lk.ijse.controller.Employee;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.EmployeeModel;
import lk.ijse.regex.RegexPattern;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeFormController {
    public JFXComboBox<String> comboBox;
    public JFXTextField txtEmpId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtSalary;
    public TableView tblEmployee;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colSalary;
    public TableColumn colType;
    public TableColumn Action;
    public AnchorPane EmployeeRoot;
    public Label lblempId;

    private EmployeeModel model=new EmployeeModel();

    private ObservableList<EmployeeTm> obList;

    public void initialize(){
        comboBox.setItems(FXCollections.observableArrayList("Admin","cashier","Manager","worker"));
        generateNextEmpId();
        setCellValue();
        getAllEmployee();
    }

    private void setCellValue() {
        colId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        Action.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void getAllEmployee() {
        var model=new EmployeeModel();

        obList= FXCollections.observableArrayList();

        try {
            List<EmployeeDto> allEmployee = model.getAllEmployee();

            for (EmployeeDto dto : allEmployee){
                Button button = createButton();
                obList.add(new EmployeeTm(
                        dto.getEmpId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getEmail(),
                        dto.getSalary(),
                        dto.getType(),
                        button
                ));

            }
            tblEmployee.setItems(obList);
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
            EmployeeRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            EmployeeRoot.setEffect(null);

            if (type.orElse(no) == yes) {
                int focusedIndex = tblEmployee.getSelectionModel().getSelectedIndex();
                EmployeeTm selectedEmployee = (EmployeeTm) tblEmployee.getSelectionModel().getSelectedItem();

                if (selectedEmployee != null) {
                    int cusId = selectedEmployee.getEmpId();
                    try {
                        boolean b = model.deleteEmployee(cusId);
                        if (b) {

                            Image image=new Image("/Icon/iconsDelete.png");
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Employee Delete Successfully");
                            notifications.title("Successfully");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();

                            System.out.println("delete selected");
                            obList.remove(focusedIndex);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
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

                String type = comboBox.getValue();
                int empId = Integer.parseInt(txtEmpId.getText());
                String name = txtName.getText();
                double salary = Double.parseDouble(txtSalary.getText());
                String email = txtEmail.getText();
                String address = txtAddress.getText();

                var dto=new EmployeeDto(empId,name,salary,email,type,address);

                try {
                    if (model.isExists(empId)) {
                        txtEmpId.requestFocus();
                        Image image=new Image("/Icon/icons8-cancel-50.png");
                        try {
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Employee is already registered");
                            notifications.title("Warning");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return;
                    }else {

                        if (checkValidate()){
                            boolean b = model.saveEmployee(dto);
                            if (b) {
                                getAllEmployee();
                                nullTextFieldColor();
                                Image image = new Image("/Icon/iconsOk.png");
                                try {
                                    Notifications notifications = Notifications.create();
                                    notifications.graphic(new ImageView(image));
                                    notifications.text("Employee Saved Successfully");
                                    notifications.title("Successfully");
                                    notifications.hideAfter(Duration.seconds(5));
                                    notifications.position(Pos.TOP_RIGHT);
                                    notifications.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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

    private void generateNextEmpId() {
        try {
            int empid = model.generateNextEmpId();
            txtEmpId.setText(String.valueOf("00"+empid));
            lblempId.setText("00"+empid);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnEmpUpadateOnAction(ActionEvent actionEvent) {

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
            String type = comboBox.getValue();
            int empId = Integer.parseInt(txtEmpId.getText());
            String name = txtName.getText();
            double salary = Double.parseDouble(txtSalary.getText());
            String email = txtEmail.getText();
            String address = txtAddress.getText();

            if (checkValidate()){
                var dto=new EmployeeDto(empId,name,salary,email,type,address);

                try {
                    boolean b = model.updateEmployee(dto);
                    if (b) {
                        getAllEmployee();
                        nullTextFieldColor();

                        Image image=new Image("/Icon/iconsOk.png");
                        try {
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Employee Update Successfully");
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
    }

    public void lblSearchOnAction(ActionEvent actionEvent) {
        int empId = Integer.parseInt(txtEmpId.getText());

        try {
            EmployeeDto dto = model.searchEmployee(empId);
            if (dto != null){
                txtName.setText(dto.getName());
                txtSalary.setText(String.valueOf(dto.getSalary()));
                txtEmail.setText(dto.getEmail());
                comboBox.setValue(dto.getType());
                txtSalary.setText(String.valueOf(dto.getSalary()));
                txtAddress.setText(dto.getAddress());

                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Employee Search Successfully");
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
                    notifications.text("Employee id does not exist");
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

    private boolean isEmptyCheck(){
        if (txtEmpId.getText().isEmpty() || comboBox.getValue() == null || txtAddress.getText().isEmpty() || txtEmail.getText().isEmpty()
        || txtSalary.getText().isEmpty()) {
            System.out.println("employee is empty");
            return true;
        }else {
            return false;
        }
    }

    private boolean checkValidate(){
        if(!(RegexPattern.getAddressPattern().matcher(txtAddress.getText()).matches())){
            txtAddress.requestFocus();
            txtAddress.setFocusColor(Color.RED);
            return false;
        }
        if (!(RegexPattern.getNamePattern().matcher(txtName.getText()).matches())){
            txtName.requestFocus();
            txtName.setFocusColor(Color.RED);
            return false;
        }
        if (!(RegexPattern.getEmailPattern().matcher(txtEmail.getText()).matches())){
            txtEmail.requestFocus();
            txtEmail.setFocusColor(Color.RED);
            return false;
        }
        if (!(RegexPattern.getDoublePattern().matcher(txtSalary.getText()).matches())){
            txtSalary.requestFocus();
            txtSalary.setFocusColor(Color.RED);
            return false;
        }
        return true;
    }

    private void nullTextFieldColor(){
        txtAddress.setFocusColor(Color.web("#0040ff"));
        txtName.setFocusColor(Color.web("#0040ff"));
        txtEmail.setFocusColor(Color.web("#0040ff"));
        txtSalary.setFocusColor(Color.web("#0040ff"));

    }
}
