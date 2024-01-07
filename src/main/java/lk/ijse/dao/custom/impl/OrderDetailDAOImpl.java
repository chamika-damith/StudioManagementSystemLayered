package lk.ijse.dao.custom.impl;

import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    private QueryDAO queryDAO= (QueryDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.QUERY);

    @Override
    public boolean saveOrderDetails(int orderId, List<CartTm> cartTmList) throws SQLException, ClassNotFoundException {
        for(CartTm tm : cartTmList) {
            if(!saveOrderDetail(orderId, tm)) {
                return false;
            }
        }
        return true;
    }
    public boolean saveOrderDetail(int orderId, CartTm tm) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO order_detail(orderId,itemId,qty) VALUES(?,?,?)",orderId,tm.getItemId(),tm.getQty());

    }
    @Override
    public List<OrderViewDto> getAllItems() throws SQLException, ClassNotFoundException {
        return queryDAO.getAllItems();
    }

    @Override
    public List<OrderDetail> getAllValues(int id) throws SQLException, ClassNotFoundException {
        return queryDAO.getAllOrderDetailValues(id);
    }

}
