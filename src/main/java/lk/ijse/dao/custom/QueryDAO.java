package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.BookingReportDto;
import lk.ijse.dto.ViewBookingDto;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {
    BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> getTodayBooking(Date date) throws SQLException, ClassNotFoundException;
}
