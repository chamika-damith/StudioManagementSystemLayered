package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsModel {

    public boolean saveOrderDetail(int orderId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO order_detail(orderId,itemId,qty) VALUES(?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);


        pstm.setInt(1,orderId);
        pstm.setInt(2, Integer.parseInt(tm.getItemId()));
        pstm.setInt(3, tm.getQty());
        System.out.println("order details saved");

        return pstm.executeUpdate() > 0;

    }

    public boolean saveOrderDetails(int orderId, List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            if(!saveOrderDetail(orderId, tm)) {
                return false;
            }
        }
        return true;
    }
}
