package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dto.OrderDto;

import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    private OrderDAO orderDAO= (OrderDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.ORDER);

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
        return orderDAO.placeOrder(orderDto);
    }
}
