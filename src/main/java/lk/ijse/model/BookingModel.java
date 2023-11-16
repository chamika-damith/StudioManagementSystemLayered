package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.PackageDto;

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

    public PackageDto searchPackage(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="select * from packages where packageId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();

        PackageDto dto=null;
        if (resultSet.next()){
            int packageId = resultSet.getInt("packageId");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            String type = resultSet.getString("type");

            dto=new PackageDto(packageId,name,price,type);

        }

        return dto;

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

    public List<PackageDto> getAllPackage() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM packages";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<PackageDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new PackageDto(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            ));
        }
        return dtoList;

    }
}
