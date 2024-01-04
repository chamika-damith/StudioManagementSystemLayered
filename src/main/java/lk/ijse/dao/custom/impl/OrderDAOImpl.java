package lk.ijse.dao.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.bo.custom.OrderBO;
import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDto;

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

    public boolean saveOrder(int orderId, Date orderDate, Date returnDate, int userId, int cusId, double total) throws SQLException, ClassNotFoundException {

        return SQLutil.execute("INSERT INTO orders(orderId, orderDate, returnDate, userId, cusId, totprice) VALUES(?, ?, ?, ?, ?,?)",
                orderId,orderDate,returnDate,userId,cusId,total);
    }

    @Override
    public boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {

        int orderId = orderDto.getOrderId();
        Date orderDate = orderDto.getOrderDate();
        Date returnDate = orderDto.getReturnDate();
        int userId = orderDto.getUserId();
        int cusId = orderDto.getCusId();
        double total = orderDto.getTotal();
        int buyItemQty = orderDto.getBuyItemQty();
        int qty = orderDto.getQty();

        Connection connection=null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSave = saveOrder(orderId, orderDate, returnDate, userId, cusId, total);
            if (isOrderSave) {
                System.out.println("order saved");
                boolean isItemSave = itemBO.updateItems(orderDto.getCartTmList(),qty);
                System.out.println("item saved");
                if (isItemSave) {
                    boolean isOrderDetailSave = orderDetailBO.saveOrderDetails(orderId, orderDto.getCartTmList());
                    if (isOrderDetailSave){
                        System.out.println("Order details saved");
                        connection.commit();
                        return true;
                    }else {
                        System.out.println("Failed to save order details");
                    }
                }else {
                    System.out.println("Failed to update item quantity");
                }
            }else {
                System.out.println("Failed to save the order");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
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
