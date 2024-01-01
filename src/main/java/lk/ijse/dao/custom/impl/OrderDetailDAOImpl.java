package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderItemDetailFormDto;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @Override
    public List<OrderViewDto> getAllItems() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT o.orderId,c.name,c.address,c.email,c.mobile FROM orders o JOIN customer c ON o.cusId = c.cusId");

        ArrayList<OrderViewDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new OrderViewDto(
                    resultSet.getInt("orderId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("mobile")
            ));
        }
        return dto;
    }

    @Override
    public List<OrderItemDetailFormDto> getAllValues(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT orders.orderId,orders.orderDate,orders.totprice,item.itemId,od.qty,item.description,item.price,item.name,item.category,item.img FROM orders JOIN order_detail od on orders.orderId = od.orderId JOIN item item on od.itemId = item.itemId WHERE od.orderId=?",
                id);

        ArrayList<OrderItemDetailFormDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            System.out.println("resultset");
            dtoList.add(new OrderItemDetailFormDto(
                    resultSet.getInt("orderId"),
                    resultSet.getDate("orderDate"),
                    resultSet.getInt("itemId"),
                    resultSet.getString("description"),
                    resultSet.getString("name"),
                    resultSet.getDouble("totprice"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category"),
                    resultSet.getInt("qty"),
                    resultSet.getBytes("img")
            ));
        }
        return dtoList;
    }

}
