package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface BookingBO extends SuperBO {
    boolean saveBooking(BookingDto dto) throws SQLException, ClassNotFoundException;
    List<BookingDto> getAllBooking() throws SQLException, ClassNotFoundException;
    boolean updateBooking(BookingDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteBooking(int focusedIndex) throws SQLException, ClassNotFoundException;
    boolean isExistsBooking(int id) throws SQLException, ClassNotFoundException;
    BookingDto searchBooking(int id) throws SQLException, ClassNotFoundException;
    int generateNextBookId() throws SQLException, ClassNotFoundException;
    BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException;
    boolean updateBookingStatus(int id) throws SQLException, ClassNotFoundException;
    String returnLbBookingValue() throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> ViewAllBooking() throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> getTodayBooking(Date date) throws SQLException, ClassNotFoundException;


}
