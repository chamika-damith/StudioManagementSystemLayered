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
import java.util.List;

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
            lblAllInventory.setText(ItemModel.returnLbItemlValue());
            lblOrders.setText(OrderModel.returnLbOrderlValue());
            lblEmpId.setText(EmployeeModel.returnLbEmployeeValue());
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void chart1() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();


        try {
            List<DasboardDto> dataFromDatabase = model.getChartData();

            for (DasboardDto dto : dataFromDatabase) {
                series.getData().add(new XYChart.Data<>(dto.getMonthName(), dto.getTotal()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        series.getData().add(new XYChart.Data<>("Nov ", 4));
//        series.getData().add(new XYChart.Data<>("Nov", 8));
//        series.getData().add(new XYChart.Data<>("Nov", 12));
//        series.getData().add(new XYChart.Data<>("Nov", 3));
//        series.getData().add(new XYChart.Data<>("Nov", 15));

        Linechart.getData().addAll(series);

    }


}
