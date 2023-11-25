package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {
    public static int generateNextBookId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT bookingId FROM booking ORDER BY bookingId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitBookId(resultSet.getInt(1));
        }
        return splitBookId(0);
    }

    private static int splitBookId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }


    public boolean saveBookingDto(BookingDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO booking(bookingId, eventType, date, location, empId, packageId, custId) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,dto.getBookingId());
        pstm.setString(2,dto.getEventType());
        pstm.setDate(3, (Date) dto.getDate());
        pstm.setString(4,dto.getLocation());
        pstm.setInt(5,dto.getEmpId());
        pstm.setInt(6,dto.getPackageId());
        pstm.setInt(7,dto.getCusId());

        return pstm.executeUpdate() > 0;
    }

    public List<ServiceDto> getAllPackage() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM packages";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<ServiceDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new ServiceDto(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            ));
        }
        return dtoList;

    }

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

    public BookingDto searchBooking(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="select * from booking where bookingId = ?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, id);

        ResultSet resultSet = pstm.executeQuery();

        BookingDto dto =null;
        while (resultSet.next()) {
            int bookingId = resultSet.getInt(1);
            String eventType = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String location = resultSet.getString(4);
            int empId=resultSet.getInt(5);
            int cusId=resultSet.getInt(6);
            int pkgId=resultSet.getInt(7);

            dto = new BookingDto(bookingId,eventType,date,location,empId,cusId,pkgId);
        }
        return dto;
    }

    public boolean isExists(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT bookingId FROM booking WHERE bookingId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    public boolean updateBooking(BookingDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE booking SET eventType = ? , date = ? , location=? , empId=?, packageId=? , custId=? WHERE bookingId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,dto.getEventType());
        pstm.setDate(2, (Date) dto.getDate());
        pstm.setString(3,dto.getLocation());
        pstm.setInt(4,dto.getEmpId());
        pstm.setInt(5,dto.getPackageId());
        pstm.setInt(6,dto.getCusId());
        pstm.setInt(7,dto.getBookingId());

        return pstm.executeUpdate() > 0;
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

}
