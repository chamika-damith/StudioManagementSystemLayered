package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportModel {
    public CustomerDto getCustomer(int itemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM Customer c JOIN Orders o ON c.cusId = o.cusId JOIN order_detail od ON o.orderId = od.orderId WHERE od.itemId = ?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,itemId);
        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto=null;

        while (resultSet.next()) {
            int cusId = resultSet.getInt("cusId");
            String name = resultSet.getString("name");
            String mobile = resultSet.getString("mobile");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");

            dto=new CustomerDto(cusId,name,mobile,name,address);
        }
        return dto;
    }

    public CustomerDto getAllCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM Customer";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto=null;

        while (resultSet.next()) {
            int cusId = resultSet.getInt("cusId");
            String name = resultSet.getString("name");
            String mobile = resultSet.getString("mobile");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");

            dto=new CustomerDto(cusId,name,mobile,name,address);
        }
        return dto;
    }
}
