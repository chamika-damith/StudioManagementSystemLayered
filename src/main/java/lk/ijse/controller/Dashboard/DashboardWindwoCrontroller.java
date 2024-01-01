package lk.ijse.controller.Dashboard;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.controller.Order.OrderItemDetailFormController;
import lk.ijse.dao.custom.BookingDAO;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.DashboardDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.impl.BookingDAOImpl;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dao.custom.impl.DashboardDAOImpl;
import lk.ijse.dao.custom.impl.OrderDAOImpl;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.DasboardDto;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.ViewBookingDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.ViewBookingTm;
import lk.ijse.dto.tm.ViewOrderTm;
import lk.ijse.model.*;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DashboardWindwoCrontroller {
    public LineChart<String,Number> Linechart;
    public Label lblAllInventory;
    public Label lblAllCustomerd;
    public Label lblOrders;
    public Label lblEmpId;
    public BarChart<String,Number> barChart;
    public TableView tblOrder;
    public TableColumn colOrderId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colMobile;
    public TableColumn colMore;
    public JFXTextField txtSearchOrder;
    public AnchorPane dashboardRoot;
    public TableView tblAppointment;
    public TableColumn colBookingId;
    public TableColumn colComplete;
    public TableColumn colNotComplete;
    public TableColumn colStatus;

    private boolean completeBooking=false;
    private boolean cancelBooking=false;
    private ObservableList<ViewBookingTm> obList;

    private CustomerDAO customerDAO=new CustomerDAOImpl();

    private BookingDAO bookingDAO=new BookingDAOImpl();

    private DashboardDAO dashboardDAO=new DashboardDAOImpl();

    private OrderDAO orderDAO=new OrderDAOImpl();

    public void initialize() throws ClassNotFoundException {
        chart1();
        setLblValue();
        setCellValues();
        getAllBooking();
        searchTable();
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

    private void getAllBooking() {
        var model=new viewBookingModel();

        String date= String.valueOf(LocalDate.now());

        obList= FXCollections.observableArrayList();

        try {
            List<ViewBookingDto> allItems = model.getTodayBooking(Date.valueOf(date));

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
        } catch (SQLException e) {
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

    public Button createButton(){
        Button btn=new Button("more");
        btn.getStyleClass().add("moreBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setMoreBtnAction(btn);
        return btn;
    }

    public void setLblValue() throws ClassNotFoundException {
        try {
            lblAllCustomerd.setText(customerDAO.returnLbCuslValue());
            lblAllInventory.setText(orderDAO.returnlblTotalSale());
            lblOrders.setText(orderDAO.returnLbOrderlValue());
            lblEmpId.setText(bookingDAO.returnLbBookingValue());
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void chart1() throws ClassNotFoundException {
        try {
            List<DasboardDto> dataFromDatabase = dashboardDAO.getChartData();

            dataFromDatabase.sort(Comparator.comparingInt(month -> {
                Map<String, Integer> monthOrder = new HashMap<>();
                monthOrder.put("Jan", 1);
                monthOrder.put("Feb", 2);
                monthOrder.put("Mar", 3);
                monthOrder.put("Apr", 4);
                monthOrder.put("May", 5);
                monthOrder.put("Jun", 6);
                monthOrder.put("Jul", 7);
                monthOrder.put("Aug", 8);
                monthOrder.put("Sep", 9);
                monthOrder.put("Oct", 10);
                monthOrder.put("Nov", 11);
                monthOrder.put("Dec", 12);

                String monthName = month.getMonthName();

                if (monthName != null) {
                    return monthOrder.getOrDefault(monthName, 0);
                } else {
                    return 0;
                }
            }));

            XYChart.Series<String, Number> series = new XYChart.Series<>();

            for (DasboardDto dto : dataFromDatabase) {
                series.getData().add(new XYChart.Data<>(dto.getMonthName(), dto.getTotal()));
            }

            Linechart.getData().addAll(series);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Button createMoreButton(){
        Button btn=new Button("more");
        btn.getStyleClass().add("moreBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setMoreBtnAction(btn);
        return btn;
    }
    public Button createCancelButton(){
        if (completeBooking){
            System.out.println("complete booking");
            return null;
        }else {
            Button btn=new Button("cancel");
            btn.getStyleClass().add("deleteBtn");
            btn.setCursor(Cursor.cursor("Hand"));
            setCancelBtnAction(btn);
            return btn;
        }
    }
    public Button createCompleteButton(){
        if (cancelBooking){
            System.out.println("booking canceled");
            return null;
        }else {
            Button btn=new Button("complete");
            btn.getStyleClass().add("completeBtn");
            btn.setCursor(Cursor.cursor("Hand"));
            setCompleteBtnAction(btn);
            return btn;
        }
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
                    boolean b = bookingDAO.updateBookingStatus(selectId);
                    if (b) {

                        completeBooking=true;

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
                        getAllBooking();
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
            dashboardRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to cancel appointment?", yes, no).showAndWait();

            dashboardRoot.setEffect(null);

            if (type.orElse(no) == yes) {
                int focusedIndex = tblAppointment.getSelectionModel().getSelectedIndex();
                ViewBookingTm selected = (ViewBookingTm) tblAppointment.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    int BookId = selected.getBookingId();
                    try {
                        boolean b = bookingDAO.delete(BookId);
                        if (b) {

                            cancelBooking=true;


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
                            getAllBooking();
                            searchTable();
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }


}
