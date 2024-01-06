package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.InventoryOrderViewDto;
import lk.ijse.dto.tm.InventoryOrderTm;
import lk.ijse.entity.InventoryOrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface InventoryOrderDetailDAO extends SuperDAO {
    boolean saveOrderDetails(List<InventoryOrderTm> cartTmList, int supOrderId, int qty) throws SQLException, ClassNotFoundException;
    List<InventoryOrderDetail> getAllValues(int id) throws SQLException, ClassNotFoundException;
    int getAllTotal(int id) throws SQLException, ClassNotFoundException;
    List<InventoryOrderViewDto> getAllItemsOrder() throws SQLException, ClassNotFoundException;
}
