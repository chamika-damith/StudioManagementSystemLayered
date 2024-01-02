package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.OrderDto;

import java.sql.SQLException;

public interface OrderDAO extends SuperDAO {
    int generateNextOrderId() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    String returnLbOrderlValue() throws SQLException, ClassNotFoundException;
    String returnlblTotalSale() throws SQLException, ClassNotFoundException;
    boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException;
}
