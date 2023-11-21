package lk.ijse.controller.Dashboard;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lk.ijse.dto.DasboardDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.*;

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

    private DashboardModel model=new DashboardModel();

    public void initialize(){
        chart1();
        setLblValue();
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
