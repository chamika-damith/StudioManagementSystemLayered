package lk.ijse.dao.custom;

import lk.ijse.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO {
    boolean saveOrderDetails(int orderId, List<CartTm> cartTmList) throws SQLException, ClassNotFoundException;
}
