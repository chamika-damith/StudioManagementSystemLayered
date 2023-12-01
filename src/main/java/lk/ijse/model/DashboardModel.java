package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
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

    public boolean saveUser(String userName, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO user(username, password) VALUES (?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        return pstm.executeUpdate() > 0;
    }

    public boolean updateUser(String username,String newPassword) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE user SET password=? where username=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,newPassword);
        pstm.setString(2,username);

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteUser(String username,String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM user WHERE username=? AND password=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1, username);
        pstm.setString(2, password);
        return pstm.executeUpdate() > 0;
    }
}
