package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.InventoryOrderItemDto;
import lk.ijse.dto.OrderItemDetailFormDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryOrderItemDetailModel {
    public List<InventoryOrderItemDto> getAllValues(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT so.supOrderId,so.orderDate,sod.itemId,sod.qty,so.description,so.category,item.name,item.price FROM supplier_order so JOIN suporderdetail sod on so.supOrderId = sod.supOrderId JOIN item item on sod.itemId = item.itemId WHERE sod.supOrderId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<InventoryOrderItemDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            System.out.println("resultset");
            dtoList.add(new InventoryOrderItemDto(
                    resultSet.getInt("itemId"),
                    resultSet.getInt("supOrderId"),
                    resultSet.getString("description"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category"),
                    resultSet.getInt("qty"),
                    resultSet.getDate("orderDate")
            ));
        }
        return dtoList;
    }
}
