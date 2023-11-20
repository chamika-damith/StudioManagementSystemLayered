package lk.ijse.controller.Booking;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.dto.*;
import lk.ijse.dto.tm.BookingCartTm;
import lk.ijse.model.BookingModel;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.EmployeeModel;
import lk.ijse.model.ServiceModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static lk.ijse.controller.Booking.EventType.*;

public class BookingFormController {
    public JFXComboBox cmbCustomerID;
    public MFXDatePicker appDate;
    public JFXComboBox cmbEventType;
    public JFXTextField txtAddress;
    public JFXComboBox cmbPackage;
    public Label bookId;
    public JFXComboBox cmbEmpId;
    public AnchorPane BookinRoot;
    public JFXTextField txtAppid;
    public Label lblCusName;
    public TableView tblBookingCart;
    public TableColumn colId;
    public TableColumn colEvent;
    public TableColumn colAddress;
    public TableColumn colName;
    public TableColumn colPackage;
    public TableColumn colDate;
    public TableColumn Action;
    public Label lblpkgName;
    public Label lblEmpName;

    private ObservableList<BookingCartTm> obList=FXCollections.observableArrayList();


    private BookingModel bookingModel=new BookingModel();

    private CustomerModel customerModel=new CustomerModel();

    private EmployeeModel employeeModel=new EmployeeModel();

    private ServiceModel serviceModel=new ServiceModel();


    public void initialize(){
        cmbEventType.setItems(FXCollections.observableArrayList(PHOTOGRAPHY, VIDEOGRAPHY, AUDIO_PRODUCTION,EventType.TV_SHOWS));
        generateNextBookId();
        loadCustomerIds();
        loadPackageIds();
        loadEmpIds();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEvent.setCellValueFactory(new PropertyValueFactory<>("event"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colName.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colPackage.setCellValueFactory(new PropertyValueFactory<>("pkgId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        Action.setCellValueFactory(new PropertyValueFactory<>("btn"));
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

        if(isEmptyCheck()){
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
            int cusId = Integer.parseInt(String.valueOf(cmbCustomerID.getValue()));
            Date date= Date.valueOf(appDate.getValue());
            String evenType = String.valueOf(cmbEventType.getValue());
            String address = txtAddress.getText();
            int pkg = Integer.parseInt(String.valueOf(cmbPackage.getValue()));
            int empId= Integer.parseInt(String.valueOf(cmbEmpId.getValue()));
            int bId= Integer.parseInt(txtAppid.getText());

            try {

                if (bookingModel.isExists(bId)) {
                    Image image=new Image("/Icon/icons8-cancel-50.png");
                    try {
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Booking is already registered");
                        notifications.title("Warning");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
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
                    }catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void generateNextBookId() {

        try {
            int bookingId = bookingModel.generateNextBookId();
            bookId.setText(String.valueOf("00"+bookingId));
            txtAppid.setText("00"+bookingId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnPackageOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Service/ServiceForm.fxml"));
        BookinRoot.getChildren().clear();
        BookinRoot.getChildren().add(parent);
    }

    private void loadPackageIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<ServiceDto> idList = bookingModel.getAllPackage();

            for (ServiceDto dto : idList) {
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

    public void cmbPackageOnAction(ActionEvent actionEvent) throws SQLException {
        getPkgName();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtAppid.getText());

        try {
            BookingDto dto = bookingModel.searchBooking(id);
            if (dto != null){
                txtAddress.setText(dto.getLocation());
                cmbEmpId.setValue(String.valueOf(dto.getEmpId()));
                cmbPackage.setValue(String.valueOf(dto.getPackageId()));
                setCmbEventType(dto.getEventType());
                cmbCustomerID.setValue(String.valueOf(dto.getCusId()));
                Date bookDate= (Date) dto.getDate();
                appDate.setValue(bookDate.toLocalDate());

                Image image=new Image("/Icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Booking Search Successfully");
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
                    notifications.text("Booking id does not exist");
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

    private void setCmbEventType(String eventType){
        switch (eventType){
            case "PHOTOGRAPHY":
                cmbEventType.setValue(PHOTOGRAPHY);
                System.out.println("Photography selected");
                break;
            case "VIDEOGRAPHY":
                cmbEventType.setValue(VIDEOGRAPHY);
                System.out.println("Videography selected");
                break;
            case "AUDIO_PRODUCTION":
                cmbEventType.setValue(AUDIO_PRODUCTION);
                System.out.println("Audio Production selected");
                break;
            case "TV_SHOWS":
                cmbEventType.setValue(TV_SHOWS);
                System.out.println("TV Shows selected");
                break;
            default:
                break;
        }
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("/view/Customer/CustomerForm.fxml"));
        BookinRoot.getChildren().clear();
        BookinRoot.getChildren().add(parent);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if(isEmptyCheck()){
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
            int cusId = Integer.parseInt(String.valueOf(cmbCustomerID.getValue()));
            Date date= Date.valueOf(appDate.getValue());
            String evenType = String.valueOf(cmbEventType.getValue());
            String address = txtAddress.getText();
            int pkg = Integer.parseInt(String.valueOf(cmbPackage.getValue()));
            int empId= Integer.parseInt(String.valueOf(cmbEmpId.getValue()));
            int bId= Integer.parseInt(txtAppid.getText());

            var dto=new BookingDto(bId,evenType,date,address,empId,cusId,pkg);

            try {
                boolean b = bookingModel.updateCustomer(dto);
                if (b) {
                    Image image=new Image("/Icon/iconsOk.png");
                    try {
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Booking Update Successfully");
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

    public void cmbCustomerIdOnAction(ActionEvent actionEvent) {
        String id = String.valueOf(cmbCustomerID.getValue());
        int cId=Integer.parseInt(id);

        if (id != null && !id.isEmpty()) {
            try {
                CustomerDto customerDto = customerModel.searchCustomer(cId);
                System.out.println(customerDto.getName());
                lblCusName.setText(customerDto.getName());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            System.out.println("Customer ID is null or empty.");
        }
    }

    public void btnAddToCart(ActionEvent actionEvent) throws SQLException {

        if(isEmptyCheck()){
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

            int id = Integer.parseInt(txtAppid.getText());
            String eventName = String.valueOf(cmbEventType.getValue());
            String address = txtAddress.getText();
            String pkg = String.valueOf(cmbPackage.getValue());
            int pkgid= Integer.parseInt(pkg);
            String empid= String.valueOf(cmbEmpId.getValue());
            int employeeId= Integer.parseInt(empid);
            Date date = Date.valueOf(appDate.getValue());
            Button btn = createButton();

            if (RegexPattern.getAddressPattern().matcher(address).matches()){

                setRemoveBtnAction(btn);
                btn.setCursor(Cursor.HAND);

                txtAddress.setFocusColor(Color.web("#0040ff"));

                if (bookingModel.isExists(id)){
                    Image image=new Image("/Icon/icons8-cancel-50.png");
                    try {
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Booking is already registered");
                        notifications.title("Warning");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    var cartTm=new BookingCartTm(id,eventName,address,employeeId,pkgid,date,btn);

                    obList.add(cartTm);
                    tblBookingCart.setItems(obList);

                    Image image=new Image("/Icon/iconsOk.png");
                    try {
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Booking Added to Cart");
                        notifications.title("Successfully Added");
                        notifications.hideAfter(Duration.seconds(4));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }else {
                txtAddress.requestFocus();
                txtAddress.setFocusColor(Color.RED);
                Image image=new Image("/Icon/icons8-cancel-50.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Invalid input for Address ");
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


    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            BookinRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblBookingCart.getSelectionModel().getSelectedIndex();
                BookinRoot.setEffect(null);
                obList.remove(focusedIndex);
                tblBookingCart.refresh();
            }
            BookinRoot.setEffect(null);
        });
    }

    public Button createButton(){
        Button btn=new Button("Remove");
        btn.getStyleClass().add("ActionBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        return btn;
    }

    public void getEmpName() throws SQLException {
        String empid = String.valueOf(cmbEmpId.getValue());
        int employeeId = Integer.valueOf(empid);
        EmployeeDto employeeDto = employeeModel.searchEmployee(employeeId);
        if (employeeDto != null){
            lblEmpName.setText(employeeDto.getName());
        }
    }

    public void getPkgName() throws SQLException {
        String pkgid= String.valueOf(cmbPackage.getValue());
        int pakageId= Integer.parseInt(pkgid);
        ServiceDto serviceDto = serviceModel.searchService(pakageId);
        if (serviceDto != null){
            lblpkgName.setText(serviceDto.getName());
        }
    }

    public void btnViewAppointment(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("/view/Booking/ViewIBooking.fxml"));
        BookinRoot.getChildren().clear();
        BookinRoot.getChildren().add(parent);
    }

    public void cmbEmpIdOnAction(ActionEvent actionEvent) throws SQLException {
        getEmpName();
    }

    private boolean isEmptyCheck(){
        if (txtAppid.getText().isEmpty() || (cmbCustomerID.getValue() == null) || (appDate.getValue()== null)
        || (cmbEventType.getValue() == null) || (cmbPackage.getValue() == null) || (txtAddress.getText().isEmpty())
        || (cmbEmpId.getValue() == null)){
            System.out.println("booking form field empty");
            return true;
        }else {
            return false;
        }
    }
}
