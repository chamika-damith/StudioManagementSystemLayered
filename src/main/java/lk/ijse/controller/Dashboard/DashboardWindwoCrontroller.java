package lk.ijse.controller.Dashboard;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.controller.Order.OrderItemDetailFormController;
import lk.ijse.dto.DasboardDto;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.ViewOrderTm;
import lk.ijse.model.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private DashboardModel model=new DashboardModel();

    private ObservableList<ViewOrderTm> obList;

    private OrderItemDetailFormController OIDController=new OrderItemDetailFormController();

    public void initialize(){
        chart1();
        setLblValue();
        setCellValues();
        getAllOrders();
        searchTable();
    }

    public void searchTable(){
        FilteredList<ViewOrderTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchOrder.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(viewOrderTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String itemId = String.valueOf(viewOrderTm.getOrderId());
                String name = viewOrderTm.getCustomerName().toLowerCase();

                return itemId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter);
            });
        });

        SortedList<ViewOrderTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblOrder.comparatorProperty());
        tblOrder.setItems(sortedData);
    }

    private void setCellValues() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colMore.setCellValueFactory(new PropertyValueFactory<>("btnMore"));
    }

    private void getAllOrders() {
        var OrderModel = new OrderDetailViewModel();

        obList= FXCollections.observableArrayList();

        try {
            List<OrderViewDto> allItems = OrderModel.getAllItems();

            for (OrderViewDto dto : allItems){
                Button button = createButton();
                obList.add(new ViewOrderTm(
                        dto.getOrderId(),
                        dto.getCustomerName(),
                        dto.getAddress(),
                        dto.getEmail(),
                        dto.getMobile(),
                        button
                ));

            }
            tblOrder.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Button createButton(){
        Button btn=new Button("more");
        btn.getStyleClass().add("moreBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setMoreBtnAction(btn);
        return btn;
    }

    private void setMoreBtnAction(Button btn) {

        btn.setOnAction((e) -> {

            int focusedIndex = tblOrder.getSelectionModel().getSelectedIndex();
            ViewOrderTm viewOrderTm= (ViewOrderTm) tblOrder.getSelectionModel().getSelectedItem();
            int selectId=viewOrderTm.getOrderId();
            OIDController.getIndex(selectId);

            try {
                Parent parent= FXMLLoader.load(getClass().getResource("/view/Order/OrderItemDetailForm.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setTitle("Order Item Detail");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                dashboardRoot.setEffect(new GaussianBlur());

                stage.setOnCloseRequest(event -> {
                    dashboardRoot.setEffect(null);
                });


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setLblValue(){
        try {
            lblAllCustomerd.setText(CustomerModel.returnLbCuslValue());
            lblAllInventory.setText(OrderModel.returnlblTotalSale());
            lblOrders.setText(OrderModel.returnLbOrderlValue());
            lblEmpId.setText(EmployeeModel.returnLbEmployeeValue());
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void chart1() {
        try {
            List<DasboardDto> dataFromDatabase = model.getChartData();

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



}
