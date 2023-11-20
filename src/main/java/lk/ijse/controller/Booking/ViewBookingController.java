package lk.ijse.controller.Booking;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.ViewBookingDto;
import lk.ijse.dto.tm.ViewBookingTm;
import lk.ijse.dto.tm.ViewOrderTm;
import lk.ijse.model.viewBookingModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

    private ObservableList<ViewBookingTm> obList;

    public void initialize(){
        setCellValues();
        getAllAppointment();
        searchTable();
    }

    private void getAllAppointment() {
        var ItemOrderModel = new viewBookingModel();

        obList= FXCollections.observableArrayList();

        try {
            List<ViewBookingDto> allItems = ItemOrderModel.getAllItems();

            for (ViewBookingDto dto : allItems){
                Button morebtn = createMoreButton();
                Button cancelbtn = createCancelButton();
                Button completebtn = createCompleteButton();
                obList.add(new ViewBookingTm(
                        dto.getBookingId(),
                        dto.getCusName(),
                        dto.getAddress(),
                        dto.getEmail(),
                        dto.getMobile(),
                        completebtn,
                        cancelbtn,
                        morebtn
                ));

            }
            tblAppointment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        btn.getStyleClass().add("moreBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setMoreBtnAction(btn);
        return btn;
    }
    public Button createCompleteButton(){
        Button btn=new Button("complete");
        btn.getStyleClass().add("moreBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setMoreBtnAction(btn);
        return btn;
    }

    private void setMoreBtnAction(Button btn) {

        btn.setOnAction((e) -> {

            int focusedIndex = tblAppointment.getSelectionModel().getSelectedIndex();
            ViewBookingTm viewBookingTm= (ViewBookingTm) tblAppointment.getSelectionModel().getSelectedItem();
            int selectId=viewBookingTm.getBookingId();

        });
    }


    public void btnNewAppoinment(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Booking/BookingForm.fxml"));
        viewAppointmentRoot.getChildren().clear();
        viewAppointmentRoot.getChildren().add(parent);
    }

}
