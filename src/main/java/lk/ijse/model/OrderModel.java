package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {
    public static int generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getInt(1));
        }
        return splitOrderId(0);
    }

    private static int splitOrderId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    public boolean isExists(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT orderId FROM orders WHERE orderId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    public boolean saveOrder(OrderDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO orders (orderId,description,orderDate,returnDate,userId,cusId,price) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,dto.getOrderId());
        pstm.setString(2,dto.getDescription());
        pstm.setDate(3,dto.getOrderDate());
        pstm.setDate(4,dto.getReturnDate());
        pstm.setInt(5,dto.getUserId());
        pstm.setInt(6,dto.getCusId());
        pstm.setDouble(7,dto.getTotal());

        return pstm.executeUpdate() > 0;
    }
}
