package lk.ijse.model;

import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dao.custom.impl.ItemDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.InventoryOrderDto;
import lk.ijse.dto.tm.InventoryOrderTm;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceInventoryOrderModel {

    private InventoryOrderDetailModel inventoryOrderDetailModel=new InventoryOrderDetailModel();

    private InventoryOrderModel inventoryOrderModel=new InventoryOrderModel();

    private ItemDAO itemDAO=new ItemDAOImpl();

    public boolean placeOrder(InventoryOrderDto dto) throws SQLException {
        Connection connection=null;

        try {

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSave = inventoryOrderModel.saveOrder(dto.getSupOrderId(), dto.getDescription(), dto.getOrderDate(), dto.getReturnDate(), dto.getCategory(), dto.getSupId());
            if (isOrderSave) {
                System.out.println("Inventory Order saved successfully");
                boolean isItemUpdate = itemDAO.updateInventoryOrderItem(dto.getCartTmList(), dto.getTxtqty());
                if (isItemUpdate) {
                    System.out.println("item updated successfully");
                    boolean isOrderDetailSave = inventoryOrderDetailModel.saveOrderDetails(dto.getCartTmList(), dto.getSupOrderId(), dto.getQty());
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
