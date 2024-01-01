package lk.ijse.dao.custom;

import lk.ijse.dto.InventoryOrderDto;

import java.sql.SQLException;

public interface InventoryOrderDAO {
    int generateNextOrderId() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    boolean placeOrder(InventoryOrderDto dto) throws SQLException, ClassNotFoundException;
}
