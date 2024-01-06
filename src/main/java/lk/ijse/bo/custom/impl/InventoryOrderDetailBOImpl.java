package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.InventoryOrderBO;
import lk.ijse.bo.custom.InventoryOrderDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.InventoryOrderDAO;
import lk.ijse.dao.custom.InventoryOrderDetailDAO;
import lk.ijse.dto.InventoryOrderDto;
import lk.ijse.dto.InventoryOrderItemDto;
import lk.ijse.dto.InventoryOrderViewDto;
import lk.ijse.dto.tm.InventoryOrderTm;
import lk.ijse.entity.InventoryOrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryOrderDetailBOImpl implements InventoryOrderDetailBO {

    private InventoryOrderDetailDAO inventoryOrderDetailDAO= (InventoryOrderDetailDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.INVENTORYDETAIL);


    @Override
    public boolean saveOrderDetails(List<InventoryOrderTm> cartTmList, int supOrderId, int qty) throws SQLException, ClassNotFoundException {
        return inventoryOrderDetailDAO.saveOrderDetails(cartTmList,supOrderId,qty);
    }

    @Override
    public List<InventoryOrderItemDto> getAllValues(int id) throws SQLException, ClassNotFoundException {
        List<InventoryOrderDetail> allValues = inventoryOrderDetailDAO.getAllValues(id);
        ArrayList<InventoryOrderItemDto> allItems = new ArrayList<InventoryOrderItemDto>();
        for (InventoryOrderDetail item : allValues){
            allItems.add(new InventoryOrderItemDto(item.getItemId(),
                    item.getSupOrderId(),
                    item.getDescription(),
                    item.getName(),
                    item.getPrice(),
                    item.getCategory(),
                    item.getQty(),
                    item.getOrderDate())
            );
        }
        return allItems;
    }

    @Override
    public int getAllTotal(int id) throws SQLException, ClassNotFoundException {
        return inventoryOrderDetailDAO.getAllTotal(id);
    }

    @Override
    public List<InventoryOrderViewDto> getAllItemsOrder() throws SQLException, ClassNotFoundException {
        return inventoryOrderDetailDAO.getAllItemsOrder();
    }
}
