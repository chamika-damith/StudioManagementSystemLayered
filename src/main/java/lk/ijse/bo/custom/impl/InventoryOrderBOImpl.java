package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.InventoryOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.InventoryOrderDAO;
import lk.ijse.dao.custom.InventoryOrderDetailDAO;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.InventoryOrderDto;
import lk.ijse.entity.InventoryOrder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class InventoryOrderBOImpl implements InventoryOrderBO {

    private InventoryOrderDAO inventoryOrderDAO= (InventoryOrderDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.INVENTORY);

    private ItemDAO itemDAO= (ItemDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.ITEM);

    private InventoryOrderDetailDAO inventoryOrderDetailDAO= (InventoryOrderDetailDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.INVENTORYDETAIL);

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
        Connection connection=null;

        try {

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSave = inventoryOrderDAO.saveOrder(dto.getSupOrderId(), dto.getDescription(), dto.getOrderDate(), dto.getReturnDate(), dto.getCategory(), dto.getSupId());
            if (isOrderSave) {
                System.out.println("Inventory Order saved successfully");
                boolean isItemUpdate = itemDAO.updateInventoryOrderItem(dto.getCartTmList(), dto.getTxtqty());
                if (isItemUpdate) {
                    System.out.println("item updated successfully");
                    boolean isOrderDetailSave = inventoryOrderDetailDAO.saveOrderDetails(dto.getCartTmList(), dto.getSupOrderId(), dto.getQty());
                    if (isOrderDetailSave) {
                        System.out.println("order detail saved successfully");
                        connection.commit();
                        return true;
                    }else {
                        System.out.println("not saved inventory order detail");
                    }
                }else {
                    System.out.println("not update item");
                }
            }else {
                System.out.println("not saved inventory order");
            }
        }catch (SQLException e){
            e.printStackTrace();try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
}
