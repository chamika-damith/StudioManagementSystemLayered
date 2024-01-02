package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.InventoryOrderDto;

import java.sql.SQLException;

public interface InventoryOrderDAO extends SuperDAO {
    int generateNextOrderId() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    boolean placeOrder(InventoryOrderDto dto) throws SQLException, ClassNotFoundException;
}
