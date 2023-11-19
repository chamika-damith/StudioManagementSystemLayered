package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.InventoryOrderViewDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryOrderViewModel {
    public List<InventoryOrderViewDto> getAllItemsOrder() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT o.supOrderId,s.name,s.address,s.category,s.contact FROM supplier_order o JOIN supplier s ON o.supId = s.supId";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<InventoryOrderViewDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new InventoryOrderViewDto(
                    resultSet.getInt("supOrderId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("category"),
                    resultSet.getString("contact")
            ));
        }
        return dto;
    }
}
