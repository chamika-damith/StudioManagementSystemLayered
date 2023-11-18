package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;

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
}
