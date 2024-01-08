package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDto;
import lk.ijse.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    private OrderDAO orderDAO= (OrderDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.ORDER);

    private ItemDAO itemDAO= (ItemDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.ITEM);

    private OrderDetailDAO orderDetailDAO= (OrderDetailDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.ORDERDETAIL);

    @Override
    public int generateNextOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNextOrderId();
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        return orderDAO.isExists(id);
    }

    @Override
    public String returnLbOrderlValue() throws SQLException, ClassNotFoundException {
        return orderDAO.returnLbOrderlValue();
    }

    @Override
    public String returnlblTotalSale() throws SQLException, ClassNotFoundException {
        return orderDAO.returnlblTotalSale();
    }

    @Override
    public boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection=null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSave = orderDAO.saveOrder(orderDto.getOrderId(),orderDto.getOrderDate(),orderDto.getReturnDate()
            ,orderDto.getUserId(),orderDto.getCusId(),orderDto.getTotal());
            if (isOrderSave) {
                System.out.println("order saved");
                boolean isItemSave = itemDAO.updateItems(orderDto.getCartTmList(),orderDto.getQty());
                System.out.println("item saved");
                if (isItemSave) {
                    boolean isOrderDetailSave = orderDetailDAO.saveOrderDetails(orderDto.getOrderId(), orderDto.getCartTmList());
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
