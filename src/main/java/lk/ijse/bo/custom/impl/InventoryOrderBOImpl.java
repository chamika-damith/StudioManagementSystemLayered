package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.InventoryOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.InventoryOrderDAO;
import lk.ijse.dto.InventoryOrderDto;

import java.sql.Date;
import java.sql.SQLException;

public class InventoryOrderBOImpl implements InventoryOrderBO {

    private InventoryOrderDAO inventoryOrderDAO= (InventoryOrderDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.INVENTORY);

    @Override
    public int generateNextOrderId() throws SQLException, ClassNotFoundException {
        return inventoryOrderDAO.generateNextOrderId();
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        return inventoryOrderDAO.isExists(id);
    }

    @Override
    public boolean placeOrder(InventoryOrderDto dto) throws SQLException, ClassNotFoundException {
        return inventoryOrderDAO.placeOrder(dto);
    }
}
