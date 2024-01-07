package lk.ijse.dao.custom.impl;

import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.BookingDAO;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.entity.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private QueryDAO queryDAO= (QueryDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.QUERY);

    @Override
    public boolean save(Booking entity) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO booking(bookingId, eventType, date, location, empId, packageId, custId) VALUES (?,?,?,?,?,?,?)",
                entity.getBookingId(),entity.getEventType(),(Date) entity.getDate(),entity.getLocation(),entity.getEmpId(),entity.getPackageId(),entity.getCusId());
    }

    @Override
    public List<Booking> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Booking entity) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE booking SET eventType = ? , date = ? , location=? , empId=?, packageId=? , custId=? WHERE bookingId=?",
                entity.getEventType(), (Date) entity.getDate(),entity.getLocation(),entity.getEmpId(),entity.getPackageId(),entity.getCusId(),entity.getBookingId());
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
    public Booking search(int id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("select * from booking where bookingId = ?",id);

        Booking entity =null;
        while (resultSet.next()) {
            int bookingId = resultSet.getInt(1);
            String eventType = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String location = resultSet.getString(4);
            int empId=resultSet.getInt(5);
            int cusId=resultSet.getInt(6);
            int pkgId=resultSet.getInt(7);

            entity = new Booking(bookingId,eventType,date,location,empId,cusId,pkgId);
        }
        return entity;
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
    public BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException {
        return queryDAO.getReportDetail(id);
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

    @Override
    public List<ViewBookingDto> getAllBooking() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLutil.execute("SELECT b.bookingId,b.status,c.name,b.location,c.email,c.mobile FROM booking b JOIN customer c ON b.custId = c.cusId");

        ArrayList<ViewBookingDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new ViewBookingDto(
                    resultSet.getInt("bookingId"),
                    resultSet.getString("name"),
                    resultSet.getString("location"),
                    resultSet.getString("email"),
                    resultSet.getString("mobile"),
                    resultSet.getBoolean("status")
            ));
        }
        return dto;
    }

    @Override
    public List<ViewBookingDto> getTodayBooking(Date date) throws SQLException, ClassNotFoundException {
        return queryDAO.getTodayBooking(date);
    }
}
