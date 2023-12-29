package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM employee";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<EmployeeDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new EmployeeDto(
                    resultSet.getInt("empId"),
                    resultSet.getString("name"),
                    resultSet.getDouble("salary"),
                    resultSet.getString("email"),
                    resultSet.getString("type"),
                    resultSet.getString("address")
            ));
        }
        return dtoList;

    }

    public BookingReportDto getReportDetail(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT b.bookingId,p.name,p.price,SUM(p.price) OVER () AS total FROM booking b JOIN packages p on b.packageId = p.packageId WHERE b.bookingId=? GROUP BY b.bookingId";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, id);

        ResultSet resultSet = pstm.executeQuery();

        BookingReportDto dto =null;
        while (resultSet.next()) {
            int bookingId = resultSet.getInt("bookingId");
            String name = resultSet.getString("name");
            double price=resultSet.getDouble("price");
            int total=resultSet.getInt("total");


            dto = new BookingReportDto(bookingId,name,price,total);
        }
        System.out.println(dto.getBookingId());
        return dto;
    }

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
