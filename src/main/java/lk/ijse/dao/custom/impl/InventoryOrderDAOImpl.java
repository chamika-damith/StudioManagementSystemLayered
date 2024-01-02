package lk.ijse.dao.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.InventoryOrderBO;
import lk.ijse.bo.custom.InventoryOrderDetailBO;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.InventoryOrderDAO;
import lk.ijse.dao.custom.InventoryOrderDetailDAO;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.InventoryOrderDto;
import java.sql.*;

public class InventoryOrderDAOImpl implements InventoryOrderDAO {

    private InventoryOrderDetailBO inventoryOrderDetailBO= (InventoryOrderDetailBO) BOFactory.getFactory().getBO(BOFactory.BOTypes.INVENTORYORDER);

    private ItemDAO itemDAO=new ItemDAOImpl();

    @Override
    public int generateNextOrderId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT supOrderId FROM supplier_order ORDER BY supOrderId DESC LIMIT 1");
        if(resultSet.next()) {
            return splitOrderId(resultSet.getInt(1));
        }
        return splitOrderId(0);
    }

    private static int splitOrderId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT supOrderId FROM supplier_order WHERE supOrderId=?",id);
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    public boolean saveOrder(int supOrderId, String description, Date orderDate, Date returnDate, String category, int supId) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO supplier_order (supOrderId,description,orderDate,returnDate,category,supId) VALUES(?,?,?,?,?,?)",
                supOrderId,description,orderDate,returnDate,category,supId);
    }

    @Override
    public boolean placeOrder(InventoryOrderDto dto) throws SQLException, ClassNotFoundException {
        Connection connection=null;

        try {

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSave = saveOrder(dto.getSupOrderId(), dto.getDescription(), dto.getOrderDate(), dto.getReturnDate(), dto.getCategory(), dto.getSupId());
            if (isOrderSave) {
                System.out.println("Inventory Order saved successfully");
                boolean isItemUpdate = itemDAO.updateInventoryOrderItem(dto.getCartTmList(), dto.getTxtqty());
                if (isItemUpdate) {
                    System.out.println("item updated successfully");
                    boolean isOrderDetailSave = inventoryOrderDetailBO.saveOrderDetails(dto.getCartTmList(), dto.getSupOrderId(), dto.getQty());
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
