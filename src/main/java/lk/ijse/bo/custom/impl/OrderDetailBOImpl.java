package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dto.OrderItemDetailFormDto;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailBOImpl implements OrderDetailBO {

    private OrderDetailDAO orderDetailDAO= (OrderDetailDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.ORDERDETAIL);
    @Override
    public boolean saveOrderDetails(int orderId, List<CartTm> cartTmList) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.saveOrderDetails(orderId, cartTmList);
    }

    @Override
    public List<OrderViewDto> getAllItems() throws SQLException, ClassNotFoundException {
        return orderDetailDAO.getAllItems();
    }

    @Override
    public List<OrderItemDetailFormDto> getAllValues(int id) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.getAllValues(id);
    }
}
