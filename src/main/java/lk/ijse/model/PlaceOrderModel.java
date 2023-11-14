package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class PlaceOrderModel {

    private OrderModel orderModel=new OrderModel();
    private ItemModel itemModel=new ItemModel();
    private OrderDetailsModel orderDetailsModel=new OrderDetailsModel();

    public boolean placeOrder(OrderDto orderDto) throws SQLException {

        int orderId = orderDto.getOrderId();
        Date orderDate = orderDto.getOrderDate();
        Date returnDate = orderDto.getReturnDate();
        int userId = orderDto.getUserId();
        int cusId = orderDto.getCusId();
        double total = orderDto.getTotal();
        int qty = orderDto.getQty();
        int itemId = orderDto.getItemId();
        int buyItemQty = orderDto.getBuyItemQty();

        Connection connection=null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSave = orderModel.saveOrder(orderId, orderDate, returnDate, userId, cusId, total);
            if (isOrderSave) {
                System.out.println("order saved");
                boolean isItemSave = itemModel.updateQty(itemId, qty);
                System.out.println("item saved");
                if (isItemSave) {
                    boolean isOrderDetailSave = orderDetailsModel.saveOrderDetails(orderId, itemId, buyItemQty);
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
