package lk.ijse.controller.Booking;

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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BookingBO;
import lk.ijse.dao.custom.BookingDAO;
import lk.ijse.dao.custom.impl.BookingDAOImpl;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.ViewBookingDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.ViewBookingTm;
import lk.ijse.dto.tm.ViewOrderTm;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ViewBookingController {
    public TableView tblAppointment;
    public TableColumn colBookingId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colMobile;
    public TableColumn colComplete;
    public TableColumn colNotComplete;
    public TableColumn colMore;
    public JFXTextField txtSearchOrder;
    public AnchorPane viewAppointmentRoot;
    public TableColumn colStatus;

    private ObservableList<ViewBookingTm> obList;

    private BookingBO bookingBO= (BookingBO) BOFactory.getFactory().getBO(BOFactory.BOTypes.BOOKING);

    public void initialize(){
        setCellValues();
        getAllAppointment();
        searchTable();
    }

    private void getAllAppointment() {
        obList= FXCollections.observableArrayList();

        try {
            List<ViewBookingDto> allItems = bookingBO.ViewAllBooking();

            for (ViewBookingDto dto : allItems){
                Button morebtn = createMoreButton();
                Button cancelbtn = createCancelButton();
                Button completebtn = createCompleteButton();
                String status = isStatus(dto.isStatus());
                Button statusbtn = createStatusButton(status);
                obList.add(new ViewBookingTm(
                        dto.getBookingId(),
                        dto.getCusName(),
                        dto.getAddress(),
                        dto.getEmail(),
                        dto.getMobile(),
                        completebtn,
                        cancelbtn,
                        morebtn,
                        statusbtn
                ));

            }
            tblAppointment.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String isStatus(boolean status) {
        if (status){
            return "GREEN";
        }else {
            return "RED";
        }

    }

    private void setCellValues() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colMore.setCellValueFactory(new PropertyValueFactory<>("more"));
        colComplete.setCellValueFactory(new PropertyValueFactory<>("complete"));
        colNotComplete.setCellValueFactory(new PropertyValueFactory<>("cancel"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void searchTable(){
        FilteredList<ViewBookingTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchOrder.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(viewBookingTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String itemId = String.valueOf(viewBookingTm.getBookingId());
                String name = viewBookingTm.getCusName().toLowerCase();

                return itemId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter);
            });
        });

        SortedList<ViewBookingTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblAppointment.comparatorProperty());
        tblAppointment.setItems(sortedData);
    }

    public Button createMoreButton(){
        Button btn=new Button("more");
        btn.getStyleClass().add("moreBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setMoreBtnAction(btn);
        return btn;
    }
    public Button createCancelButton(){
        Button btn=new Button("cancel");
        btn.getStyleClass().add("deleteBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setCancelBtnAction(btn);
        return btn;
    }
    public Button createCompleteButton(){
        Button btn=new Button("complete");
        btn.getStyleClass().add("completeBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setCompleteBtnAction(btn);
        return btn;
    }

    public Button createStatusButton(String color){
        Button btn=new Button();
        btn.setStyle("-fx-background-color: " + color + ";");
        btn.getStyleClass().add("statusBtn");
        btn.setCursor(Cursor.NONE);
        return btn;
    }

    private void setCompleteBtnAction(Button btn) {

        btn.setOnAction((e) -> {

            int focusedIndex = tblAppointment.getSelectionModel().getSelectedIndex();
            ViewBookingTm viewBookingTm= (ViewBookingTm) tblAppointment.getSelectionModel().getSelectedItem();
            int selectId=viewBookingTm.getBookingId();

            if (selectId !=0) {
                try {
                    boolean b = bookingBO.updateBookingStatus(selectId);
                    if (b) {

                        Image image=new Image("/Icon/iconsOk.png");
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Booking Complete Successfully");
                        notifications.title("Successfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();

                        System.out.println("booking complete selected");
                        obList.remove(focusedIndex);
                        getAllAppointment();
                        searchTable();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
    }

    private void setMoreBtnAction(Button btn) {

        btn.setOnAction((e) -> {

            int focusedIndex = tblAppointment.getSelectionModel().getSelectedIndex();
            ViewBookingTm viewBookingTm= (ViewBookingTm) tblAppointment.getSelectionModel().getSelectedItem();
            int selectId=viewBookingTm.getBookingId();

        });
    }

    private void setCancelBtnAction(Button btn) {

        btn.setOnAction((e) -> {
            viewAppointmentRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to cancel appointment?", yes, no).showAndWait();

            viewAppointmentRoot.setEffect(null);

            if (type.orElse(no) == yes) {
                int focusedIndex = tblAppointment.getSelectionModel().getSelectedIndex();
                ViewBookingTm selected = (ViewBookingTm) tblAppointment.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    int BookId = selected.getBookingId();
                    try {
                        boolean b = bookingBO.deleteBooking(BookId);
                        if (b) {

                            Image image=new Image("/Icon/iconsDelete.png");
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Booking Cancel Successfully");
                            notifications.title("Successfully");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();

                            System.out.println("cancel booking selected");
                            obList.remove(focusedIndex);
                            getAllAppointment();
                            searchTable();
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }


    public void btnNewAppoinment(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Booking/BookingForm.fxml"));
        viewAppointmentRoot.getChildren().clear();
        viewAppointmentRoot.getChildren().add(parent);
    }

}
