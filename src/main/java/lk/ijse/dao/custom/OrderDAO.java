package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.Order;

import java.sql.Date;
import java.sql.SQLException;

public interface OrderDAO extends SuperDAO {
    int generateNextOrderId() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    String returnLbOrderlValue() throws SQLException, ClassNotFoundException;
    String returnlblTotalSale() throws SQLException, ClassNotFoundException;
    boolean saveOrder(int orderId, Date orderDate, Date returnDate, int userId, int cusId, double total) throws SQLException, ClassNotFoundException;
}
