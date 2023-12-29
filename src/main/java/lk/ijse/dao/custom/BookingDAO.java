package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.BookingReportDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.ServiceDto;

import java.sql.SQLException;
import java.util.List;

public interface BookingDAO extends CrudDAO<BookingDto> {
    List<ServiceDto> getAllPackage() throws SQLException;
    int generateNextBookId() throws SQLException, ClassNotFoundException;
    List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;
    BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException;
    boolean updateBookingStatus(int id) throws SQLException, ClassNotFoundException;
    String returnLbBookingValue() throws SQLException, ClassNotFoundException;

}
