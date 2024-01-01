package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface BookingDAO extends CrudDAO<BookingDto> {
    List<ServiceDto> getAllPackage() throws SQLException;
    int generateNextBookId() throws SQLException, ClassNotFoundException;
    List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;
    BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException;
    boolean updateBookingStatus(int id) throws SQLException, ClassNotFoundException;
    String returnLbBookingValue() throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> getAllBooking() throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> getTodayBooking(Date date) throws SQLException, ClassNotFoundException;

}
