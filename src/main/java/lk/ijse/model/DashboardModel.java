package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.DasboardDto;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardModel {
    public List<DasboardDto> getChartData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="select monthname(o.orderDate) as monthName,sum(o.totprice) as totalPrice from orders o GROUP BY MONTH(o.orderDate), MONTHNAME(o.orderDate)";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<DasboardDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new DasboardDto(
                    resultSet.getString("monthName"),
                    resultSet.getInt("totalPrice")
            ));
        }
        return dtoList;
    }
}
