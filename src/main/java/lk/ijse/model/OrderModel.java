package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDto;

import java.sql.*;

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

    public boolean saveOrder(int orderId, Date orderDate, Date returnDate, int userId, int cusId, double total) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO orders(orderId, orderDate, returnDate, userId, cusId, price) VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, orderId);
        pstm.setDate(2, orderDate);
        pstm.setDate(3, returnDate);
        pstm.setInt(4, userId);
        pstm.setInt(5, cusId);
        pstm.setDouble(6, total);

        return pstm.executeUpdate() > 0;
    }
}
