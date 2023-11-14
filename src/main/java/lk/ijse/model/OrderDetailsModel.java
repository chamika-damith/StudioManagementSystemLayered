package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailsModel {

    public boolean saveOrderDetails(int orderId, int itemId, int buyItemQty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO order_detail(orderId,itemId,qty) VALUES(?,?,?)";

        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,orderId);
        pstm.setInt(2,itemId);
        pstm.setInt(3,buyItemQty);

        System.out.println("order details updated");

        return pstm.executeUpdate() > 0;

    }
}
