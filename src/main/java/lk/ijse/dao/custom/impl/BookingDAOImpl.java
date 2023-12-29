package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.BookingDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.BookingReportDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.ServiceDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public boolean save(BookingDto dto) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO booking(bookingId, eventType, date, location, empId, packageId, custId) VALUES (?,?,?,?,?,?,?)",
                dto.getBookingId(),dto.getEventType(),(Date) dto.getDate(),dto.getLocation(),dto.getEmpId(),dto.getPackageId(),dto.getCusId());
    }

    @Override
    public List<BookingDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
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

    @Override
    public boolean update(BookingDto dto) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE booking SET eventType = ? , date = ? , location=? , empId=?, packageId=? , custId=? WHERE bookingId=?",
                dto.getEventType(), (Date) dto.getDate(),dto.getLocation(),dto.getEmpId(),dto.getPackageId(),dto.getCusId(),dto.getBookingId());
    }

    @Override
    public boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM booking WHERE bookingId=?",focusedIndex);
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT bookingId FROM booking WHERE bookingId=?",id);
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public BookingDto search(int id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("select * from booking where bookingId = ?",id);

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

    @Override
    public int generateNextBookId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT bookingId FROM booking ORDER BY bookingId DESC LIMIT 1");
        if(resultSet.next()) {
            return splitBookId(resultSet.getInt(1));
        }
        return splitBookId(0);
    }

    private int splitBookId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    @Override
    public List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT * FROM employee");
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

    @Override
    public BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT b.bookingId,p.name,p.price,SUM(p.price) OVER () AS total FROM booking b JOIN packages p on b.packageId = p.packageId WHERE b.bookingId=? GROUP BY b.bookingId",id);

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

    @Override
    public boolean updateBookingStatus(int id) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE booking SET status=true WHERE bookingId=?",id);
    }

    @Override
    public String returnLbBookingValue() throws SQLException, ClassNotFoundException {
        String BookCount;
        ResultSet resultSet = SQLutil.execute("SELECT COUNT(bookingId) FROM booking where status=false");
        while (resultSet.next()){
            BookCount= String.valueOf(resultSet.getInt(1));
            return BookCount;
        }
        return null;
    }
}
