package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

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

}
