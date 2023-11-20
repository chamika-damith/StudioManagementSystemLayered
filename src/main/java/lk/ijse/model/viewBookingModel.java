package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.ViewBookingDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class viewBookingModel {
    public List<ViewBookingDto> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT b.bookingId,c.name,b.location,c.email,c.mobile FROM booking b JOIN customer c ON b.custId = c.cusId";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<ViewBookingDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new ViewBookingDto(
                    resultSet.getInt("bookingId"),
                    resultSet.getString("name"),
                    resultSet.getString("location"),
                    resultSet.getString("email"),
                    resultSet.getString("mobile")
            ));
        }
        return dto;
    }
}
