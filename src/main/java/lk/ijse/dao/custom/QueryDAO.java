package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.BookingReportDto;
import lk.ijse.dto.InventoryOrderViewDto;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.ViewBookingDto;
import lk.ijse.entity.InventoryOrderDetail;
import lk.ijse.entity.OrderDetail;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {
    BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> getTodayBooking(Date date) throws SQLException, ClassNotFoundException;
    List<ViewBookingDto> getAllBooking() throws SQLException, ClassNotFoundException;
    List<InventoryOrderDetail> getAllValues(int id) throws SQLException, ClassNotFoundException;
    int getAllTotal(int id) throws SQLException, ClassNotFoundException;
    List<InventoryOrderViewDto> getAllItemsOrder() throws SQLException, ClassNotFoundException;
    List<OrderViewDto> getAllItems() throws SQLException, ClassNotFoundException;
    List<OrderDetail> getAllOrderDetailValues(int id) throws SQLException, ClassNotFoundException;
}
