package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.BookingDAO;
import lk.ijse.dto.BookingDto;

import java.sql.Date;
import java.sql.SQLException;
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
    public boolean update(BookingDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BookingDto search(int id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
