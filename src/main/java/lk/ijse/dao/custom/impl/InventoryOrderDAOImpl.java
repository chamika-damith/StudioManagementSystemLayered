package lk.ijse.dao.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.InventoryOrderDetailBO;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.InventoryOrderDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.InventoryOrder;

import java.sql.*;

public class InventoryOrderDAOImpl implements InventoryOrderDAO {

    private InventoryOrderDetailBO inventoryOrderDetailBO= (InventoryOrderDetailBO) BOFactory.getFactory().getBO(BOFactory.BOTypes.INVENTORYORDER);

    private ItemBO itemBO= (ItemBO) BOFactory.getFactory().getBO(BOFactory.BOTypes.ITEM);

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

    @Override
    public boolean saveOrder(int supOrderId, String description, Date orderDate, Date returnDate, String category, int supId) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO supplier_order (supOrderId,description,orderDate,returnDate,category,supId) VALUES(?,?,?,?,?,?)",
                supOrderId,description,orderDate,returnDate,category,supId);
    }

}
