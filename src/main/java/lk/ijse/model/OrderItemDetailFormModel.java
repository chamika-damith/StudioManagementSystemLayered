package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderItemDetailFormDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDetailFormModel {
    public List<OrderItemDetailFormDto> getAllValues(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT orders.orderId,orders.orderDate,orders.totprice,item.itemId,od.qty,item.description,item.price,item.name,item.category,item.img FROM orders JOIN order_detail od on orders.orderId = od.orderId JOIN item item on od.itemId = item.itemId WHERE od.orderId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<OrderItemDetailFormDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            System.out.println("resultset");
            dtoList.add(new OrderItemDetailFormDto(
                    resultSet.getInt("orderId"),
                    resultSet.getDate("orderDate"),
                    resultSet.getInt("itemId"),
                    resultSet.getString("description"),
                    resultSet.getString("name"),
                    resultSet.getDouble("totprice"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category"),
                    resultSet.getInt("qty"),
                    resultSet.getBytes("img")
            ));
        }
        return dtoList;
    }
}
