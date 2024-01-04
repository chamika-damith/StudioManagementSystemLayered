package lk.ijse.controller.Supplier;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import lk.ijse.controller.Customer.CustomerFormController;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.SupplierTm;
import lk.ijse.regex.RegexPattern;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static lk.ijse.controller.Customer.CustomerFormController.id;

public class SupplierFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtMobile;
    public JFXComboBox cmbCategory;
    public AnchorPane supplierRoot;
    public TableView tblSupplier;
    public Label lblSupplier;
    public Label lblSupId;
    public JFXTextField txtCostSearchTable;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colMobile;
    public TableColumn colCategory;
    public TableColumn Action;

    private ObservableList<SupplierTm> obList;



    private SupplierDAO supplierDAO=new SupplierDAOImpl();

    public void initialize() throws ClassNotFoundException {
        cmbCategory.setItems(FXCollections.observableArrayList("CAMERA", "LENS", "DRONE", "LIGHTS", "ACCESORIES"));
        generateNextSupId();
        setCellValue();
        getAllSupplier();
        searchTable();
    }

    private void setCellValue() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        Action.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    public void txtIdOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        int id = Integer.parseInt(txtId.getText());

        try {
            SupplierDto dto = supplierDAO.search(id);
            if (dto != null){
                txtName.setText(dto.getName());
                txtMobile.setText(dto.getContact());
                txtAddress.setText(dto.getAddress());
                cmbCategory.setValue(dto.getCategory());

                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Supplier Search Successfully");
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
                    notifications.text("Supplier id does not exist");
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

    public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {

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

            if (checkValidate()){
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                String address = txtAddress.getText();
                String mobile = txtMobile.getText();
                String category = (String) cmbCategory.getValue();

                try {
                    if (supplierDAO.isExists(id)) {
                        clearField();
                        Image image = new Image("/Icon/icons8-cancel-50.png");
                        try {
                            Notifications notifications = Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Supplier is already registered");
                            notifications.title("Warning");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        var dto = new SupplierDto(id, name, mobile, address, category);

                        try {
                            boolean b = supplierDAO.save(dto);
                            if (b) {
                                clearField();
                                generateNextSupId();
                                getAllSupplier();
                                searchTable();
                                Image image = new Image("/Icon/iconsOk.png");
                                try {
                                    Notifications notifications = Notifications.create();
                                    notifications.graphic(new ImageView(image));
                                    notifications.text("Supplier Saved Successfully");
                                    notifications.title("Successfully");
                                    notifications.hideAfter(Duration.seconds(5));
                                    notifications.position(Pos.TOP_RIGHT);
                                    notifications.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
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

    public void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {

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

            if (checkValidate()){
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                String address = txtAddress.getText();
                String mobile = txtMobile.getText();
                String category = (String) cmbCategory.getValue();


                var dto = new SupplierDto(id, name, address, mobile, category);


                try {
                    boolean b = supplierDAO.update(dto);
                    if (b) {
                        getAllSupplier();
                        searchTable();
                        Image image = new Image("/Icon/iconsOk.png");
                        try {
                            Notifications notifications = Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Supplier Update Successfully");
                            notifications.title("Successfully");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();
                        } catch (Exception e) {
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

    private void getAllSupplier() throws ClassNotFoundException {
        obList= FXCollections.observableArrayList();

        try {
            List<SupplierDto> allCustomer = supplierDAO.getAll();

            for (SupplierDto dto : allCustomer){
                Button button = createButton();
                obList.add(new SupplierTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getContact(),
                        dto.getCategory(),
                        button
                ));

            }
            tblSupplier.setItems(obList);
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
            supplierRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            supplierRoot.setEffect(null);

            if (type.orElse(no) == yes) {
                int focusedIndex = tblSupplier.getSelectionModel().getSelectedIndex();
                SupplierTm selected = (SupplierTm) tblSupplier.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    int supId = selected.getId();
                    try {
                        boolean b = supplierDAO.delete(supId);
                        if (b) {
                            generateNextSupId();
                            Image image=new Image("/Icon/iconsDelete.png");
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Supplier Delete Successfully");
                            notifications.title("Successfully");
                            notifications.hideAfter(Duration.seconds(4));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();

                            System.out.println("delete selected");
                            obList.remove(focusedIndex);
                            getAllSupplier();
                            searchTable();
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    public void clearField(){
        txtId.clear();
        txtMobile.clear();
        txtAddress.clear();
        txtName.clear();
        cmbCategory.getSelectionModel().clearSelection();
    }

    private void generateNextSupId() throws ClassNotFoundException {
        try {
            int supid = supplierDAO.generateNextSupId();
            txtId.setText(String.valueOf("00"+supid));
            lblSupId.setText(String.valueOf("00"+supid));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void searchTable() {
        FilteredList<SupplierTm> filteredData = new FilteredList<>(obList, b -> true);

        txtCostSearchTable.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(supplierTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String supId = String.valueOf(supplierTm.getId());
                String name = supplierTm.getName().toLowerCase();
                String category = supplierTm.getCategory().toLowerCase();

                return supId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter) || category.contains(lowerCaseFilter);
            });
        });

        SortedList<SupplierTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblSupplier.comparatorProperty());
        tblSupplier.setItems(sortedData);
    }

    private boolean isEmptyCheck(){
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtMobile.getText().isEmpty() || cmbCategory.getValue() == null){
            System.out.println("Supplier value is empty");
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
        txtAddress.setFocusColor(Color.web("#0040ff"));
    }
}
