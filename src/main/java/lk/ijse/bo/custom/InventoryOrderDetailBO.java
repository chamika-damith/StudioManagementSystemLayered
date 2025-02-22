package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.InventoryOrderItemDto;
import lk.ijse.dto.InventoryOrderViewDto;
import lk.ijse.dto.tm.InventoryOrderTm;

import java.sql.SQLException;
import java.util.List;

public interface InventoryOrderDetailBO extends SuperBO {
    boolean saveOrderDetails(List<InventoryOrderTm> cartTmList, int supOrderId, int qty) throws SQLException, ClassNotFoundException;
    List<InventoryOrderItemDto> getAllValues(int id) throws SQLException, ClassNotFoundException;
    int getAllTotal(int id) throws SQLException, ClassNotFoundException;
    List<InventoryOrderViewDto> getAllItemsOrder() throws SQLException, ClassNotFoundException;

}
