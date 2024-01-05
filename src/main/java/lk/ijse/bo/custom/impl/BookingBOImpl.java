package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BookingBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BookingDAO;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.BookingReportDto;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.ViewBookingDto;
import lk.ijse.entity.Booking;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class BookingBOImpl implements BookingBO {

    private BookingDAO bookingDAO= (BookingDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.BOOKING);

    @Override
    public boolean saveBooking(BookingDto dto) throws SQLException, ClassNotFoundException {
        return bookingDAO.save(new Booking(dto.getBookingId(),dto.getEventType(),dto.getDate(),dto.getLocation(),
                dto.getEmpId(),dto.getCusId(),dto.getPackageId()));
    }

    @Override
    public List<BookingDto> getAllBooking() throws SQLException, ClassNotFoundException {
        List<Booking> all = bookingDAO.getAll();
        List<BookingDto> bookingDto = null;
        for (Booking book : all) {
            bookingDto.add(new BookingDto(book.getBookingId(),
                    book.getEventType(),
                    book.getDate(),
                    book.getLocation(),
                    book.getEmpId(),
                    book.getCusId(),
                    book.getPackageId()));
        }
        return bookingDto;
    }

    @Override
    public boolean updateBooking(BookingDto dto) throws SQLException, ClassNotFoundException {
        return bookingDAO.update(new Booking(dto.getBookingId(),dto.getEventType(),dto.getDate(),dto.getLocation(),
                dto.getEmpId(),dto.getCusId(),dto.getPackageId()));
    }

    @Override
    public boolean deleteBooking(int focusedIndex) throws SQLException, ClassNotFoundException {
        return bookingDAO.delete(focusedIndex);
    }

    @Override
    public boolean isExistsBooking(int id) throws SQLException, ClassNotFoundException {
        return bookingDAO.isExists(id);
    }

    @Override
    public BookingDto searchBooking(int id) throws SQLException, ClassNotFoundException {
        Booking search = bookingDAO.search(id);
        BookingDto bookingDto=new BookingDto(search.getBookingId(),search.getEventType(),search.getDate(),search.getLocation(),
                search.getEmpId(),search.getCusId(),search.getPackageId());

        return bookingDto;
    }

    @Override
    public int generateNextBookId() throws SQLException, ClassNotFoundException {
        return bookingDAO.generateNextBookId();
    }

    @Override
    public BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException {
        return bookingDAO.getReportDetail(id);
    }

    @Override
    public boolean updateBookingStatus(int id) throws SQLException, ClassNotFoundException {
        return bookingDAO.updateBookingStatus(id);
    }

    @Override
    public String returnLbBookingValue() throws SQLException, ClassNotFoundException {
        return bookingDAO.returnLbBookingValue();
    }

    @Override
    public List<ViewBookingDto> ViewAllBooking() throws SQLException, ClassNotFoundException {
        return bookingDAO.getAllBooking();
    }

    @Override
    public List<ViewBookingDto> getTodayBooking(Date date) throws SQLException, ClassNotFoundException {
        return bookingDAO.getTodayBooking(date);
    }
}
