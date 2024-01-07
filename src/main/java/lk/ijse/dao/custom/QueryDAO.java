package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.BookingReportDto;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {
    BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException;
}
