package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.OrderViewDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailViewModel {

    public List<OrderViewDto> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT o.orderId,c.name,c.address,c.email,c.mobile FROM orders o JOIN customer c ON o.cusId = c.cusId";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<OrderViewDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new OrderViewDto(
                    resultSet.getInt("orderId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("mobile")
            ));
        }
        return dto;
    }
}
