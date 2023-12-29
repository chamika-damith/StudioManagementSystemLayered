package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {

    public boolean updateBookingStatus(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE booking SET status=true WHERE bookingId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);

        return pstm.executeUpdate() > 0;
    }


    public static String returnLbBookingValue() throws SQLException {
        String BookCount;
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(bookingId) FROM booking where status=false";

        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            BookCount= String.valueOf(resultSet.getInt(1));
            return BookCount;
        }
        return null;
    }
}
