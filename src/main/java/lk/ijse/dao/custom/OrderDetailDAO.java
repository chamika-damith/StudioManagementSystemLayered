package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.OrderItemDetailFormDto;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends SuperDAO {
    boolean saveOrderDetails(int orderId, List<CartTm> cartTmList) throws SQLException, ClassNotFoundException;

    List<OrderViewDto> getAllItems() throws SQLException, ClassNotFoundException;

    List<OrderItemDetailFormDto> getAllValues(int id) throws SQLException, ClassNotFoundException;
}
