package lk.ijse.controller.Service;

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
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PackageBO;
import lk.ijse.dao.custom.PackageDAO;
import lk.ijse.dao.custom.impl.PackageDAOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.ServiceTm;
import lk.ijse.regex.RegexPattern;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServiceFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtPrice;
    public JFXComboBox cmbType;
    public Label lblServiceId;
    public TableView tblEmployee;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colPrice;
    public TableColumn colType;
    public TableColumn Action;
    public JFXTextField txtCostSearchTable;
    public Label lblService;
    public AnchorPane ServiceRoot;

    private ObservableList<ServiceTm> obList;

    private PackageBO packageBO= (PackageBO) BOFactory.getFactory().getBO(BOFactory.BOTypes.PACKAGE);


    public void initialize() throws ClassNotFoundException {
        cmbType.setItems(FXCollections.observableArrayList("PHOTOGRAPHY", "VIDEOGRAPHY", "AUDIO_PRODUCTION", "TV_SHOWS"));
        txtId.requestFocus();
        generateNextPkgId();
        getAllService();
        setCellValue();
        searchTable();
    }

    private void setCellValue() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        Action.setCellValueFactory(new PropertyValueFactory<>("btn"));

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
                int serviceId = Integer.parseInt(txtId.getText());
                String type = (String) cmbType.getValue();
                double price = Double.parseDouble(txtPrice.getText());
                String name = txtName.getText();

                try {
                    if (packageBO.isExistsPackage(serviceId)) {
                        Image image = new Image("/Icon/icons8-cancel-50.png");
                        try {
                            Notifications notifications = Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Service is already Added");
                            notifications.title("Warning");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        var dto = new ServiceDto(serviceId, name, price, type);
                        boolean b = packageBO.savePackage(dto);
                        if (b) {
                            clearField();
                            getAllService();
                            generateNextPkgId();
                            searchTable();
                            Image image = new Image("/Icon/iconsOk.png");
                            try {
                                Notifications notifications = Notifications.create();
                                notifications.graphic(new ImageView(image));
                                notifications.text("service Saved Successfully");
                                notifications.title("Successfully");
                                notifications.hideAfter(Duration.seconds(5));
                                notifications.position(Pos.TOP_RIGHT);
                                notifications.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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

    private void generateNextPkgId() throws ClassNotFoundException {
        try {
            int pkgid = packageBO.generateNextPkgId();
            txtId.setText(String.valueOf("00"+pkgid));
            lblServiceId.setText("00"+pkgid);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        int id = Integer.parseInt(txtId.getText());

        try {
            ServiceDto dto = packageBO.searchPackage(id);
            if (dto != null){
                txtName.setText(dto.getName());
                txtName.setText(dto.getName());
                txtPrice.setText(String.valueOf(dto.getPrice()));
                cmbType.setValue(dto.getType());
                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Service Search Successfully");
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
                    notifications.text("Service id does not exist");
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

    private void getAllService() throws ClassNotFoundException {

        obList= FXCollections.observableArrayList();

        try {
            List<ServiceDto> allService = packageBO.getAllPackage();

            for (ServiceDto dto : allService){
                Button button = createButton();
                obList.add(new ServiceTm(
                        dto.getPkgId(),
                        dto.getName(),
                        dto.getPrice(),
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
            ServiceRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            ServiceRoot.setEffect(null);

            if (type.orElse(no) == yes) {
                int index = tblEmployee.getSelectionModel().getSelectedIndex();
                ServiceTm selected = (ServiceTm) tblEmployee.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    int id = selected.getId();
                    try {
                        boolean b = packageBO.deletePackage(id);
                        if (b) {
                            clearField();
                            generateNextPkgId();
                            Image image=new Image("/Icon/iconsDelete.png");
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Service Delete Successfully");
                            notifications.title("Successfully");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();

                            System.out.println(index);
                            obList.remove(index);
                            getAllService();
                            searchTable();
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
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
                double price = Double.parseDouble(txtPrice.getText());
                String type = (String) cmbType.getValue();

                ServiceDto dto = new ServiceDto(id, name, price, type);

                try {
                    boolean b = packageBO.updatePackage(dto);
                    if (b) {
                        getAllService();
                        searchTable();
                        clearField();
                        Image image = new Image("/Icon/iconsOk.png");
                        try {
                            Notifications notifications = Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Service Update Successfully");
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

    public void searchTable() {
        FilteredList<ServiceTm> filteredData = new FilteredList<>(obList, b -> true);

        txtCostSearchTable.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(serviceTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String ServiceId = String.valueOf(serviceTm.getId());
                String name = serviceTm.getName().toLowerCase();
                String type=serviceTm.getType().toLowerCase();

                return ServiceId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter) || type.contains(lowerCaseFilter);
            });
        });

        SortedList<ServiceTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblEmployee.comparatorProperty());
        tblEmployee.setItems(sortedData);
    }


    public void clearField(){
        txtId.clear();
        txtPrice.clear();
        txtName.clear();
        cmbType.getSelectionModel().clearSelection();
    }

    private boolean isEmptyCheck(){
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || cmbType.getValue() == null ){
            System.out.println("service value is empty");
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

        if (!(RegexPattern.getDoublePattern().matcher(txtPrice.getText()).matches())){
            txtPrice.requestFocus();
            txtPrice.setFocusColor(Color.RED);
            return false;
        }

        return true;
    }

    private void nullTextFieldColor() {
        txtPrice.setFocusColor(Color.web("#0040ff"));
        txtName.setFocusColor(Color.web("#0040ff"));
    }
}
