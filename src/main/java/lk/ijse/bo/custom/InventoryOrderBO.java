package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.InventoryOrderDto;

import java.sql.Date;
import java.sql.SQLException;

public interface InventoryOrderBO extends SuperBO {
    int generateNextOrderId() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    boolean placeOrder(InventoryOrderDto dto) throws SQLException, ClassNotFoundException;

}
