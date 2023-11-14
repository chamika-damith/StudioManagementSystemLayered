package lk.ijse.controller.Dashboard;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lk.ijse.controller.Customer.CustomerFormController;
import lk.ijse.controller.Inventory.InventoryFormController;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;
import lk.ijse.model.OrderModel;

import java.sql.SQLException;

public class DashboardWindwoCrontroller {
    public LineChart<?,?> Linechart;
    public Label lblAllInventory;
    public Label lblAllCustomerd;
    public Label lblOrders;

    public void initialize(){
        chart1();
        setLblValue();
    }

    public void setLblValue(){
        try {
            lblAllCustomerd.setText(CustomerModel.returnLbCuslValue());
            lblAllInventory.setText(ItemModel.returnLbItemlValue());
            lblOrders.setText(OrderModel.returnLbOrderlValue());
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void chart1() {
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data("Aug 1",3));
        series.getData().add(new XYChart.Data("Aug 7",8));
        series.getData().add(new XYChart.Data("Aug 14",6));
        series.getData().add(new XYChart.Data("Aug 21",10));
        series.getData().add(new XYChart.Data("Aug 28",9));
        Linechart.getData().addAll(series);

    }
}
