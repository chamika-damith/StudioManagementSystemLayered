package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.*;
import lk.ijse.entity.Booking;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface BookingDAO extends CrudDAO<Booking> {
    int generateNextBookId() throws SQLException, ClassNotFoundException;
    BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException;
    boolean updateBookingStatus(int id) throws SQLException, ClassNotFoundException;
    String returnLbBookingValue() throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> getAllBooking() throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> getTodayBooking(Date date) throws SQLException, ClassNotFoundException;

}
