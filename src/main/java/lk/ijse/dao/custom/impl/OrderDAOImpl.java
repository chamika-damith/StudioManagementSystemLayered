package lk.ijse.dao.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.Order;

import java.sql.*;

public class OrderDAOImpl implements OrderDAO {

    private ItemBO itemBO= (ItemBO) BOFactory.getFactory().getBO(BOFactory.BOTypes.ITEM);

    private OrderDetailBO orderDetailBO= (OrderDetailBO) BOFactory.getFactory().getBO(BOFactory.BOTypes.ORDERDETAIL);


    @Override
    public int generateNextOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
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
        ResultSet resultSet = SQLutil.execute("SELECT orderId FROM orders WHERE orderId=?",id);
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public String returnLbOrderlValue() throws SQLException, ClassNotFoundException {
        String orderCount;
        ResultSet resultSet = SQLutil.execute("SELECT COUNT(orderId) FROM orders");
        while (resultSet.next()){
            orderCount= String.valueOf(resultSet.getInt(1));
            return orderCount;
        }
        return null;
    }

    @Override
    public String returnlblTotalSale() throws SQLException, ClassNotFoundException {
        String sale;
        ResultSet resultSet =SQLutil.execute("SELECT SUM(totprice) FROM orders");
        while (resultSet.next()){
            sale= String.valueOf(resultSet.getInt(1));
            return sale;
        }
        return null;
    }

    @Override
    public boolean saveOrder(int orderId, Date orderDate, Date returnDate, int userId, int cusId, double total) throws SQLException, ClassNotFoundException {

        return SQLutil.execute("INSERT INTO orders(orderId, orderDate, returnDate, userId, cusId, totprice) VALUES(?, ?, ?, ?, ?,?)",
                orderId,orderDate,returnDate,userId,cusId,total);
    }

}
