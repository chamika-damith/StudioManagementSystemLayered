package lk.ijse.controller.Dashboard;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class DashboardWindwoCrontroller {
    public LineChart<?,?> Linechart;

    public void initialize(){
        chart1();
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
