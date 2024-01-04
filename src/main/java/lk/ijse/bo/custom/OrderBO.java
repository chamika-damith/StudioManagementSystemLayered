package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.OrderDto;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    int generateNextOrderId() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    String returnLbOrderlValue() throws SQLException, ClassNotFoundException;
    String returnlblTotalSale() throws SQLException, ClassNotFoundException;
    boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException;
}
