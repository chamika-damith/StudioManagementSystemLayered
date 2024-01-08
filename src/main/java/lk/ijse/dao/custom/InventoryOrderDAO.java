package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.InventoryOrder;

import java.sql.Date;
import java.sql.SQLException;

public interface InventoryOrderDAO extends SuperDAO {
    int generateNextOrderId() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    boolean saveOrder(int supOrderId, String description, Date orderDate, Date returnDate, String category, int supId) throws SQLException, ClassNotFoundException;
}
