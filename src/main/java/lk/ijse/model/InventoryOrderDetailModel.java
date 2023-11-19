package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.InventoryOrderTm;

import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InventoryOrderDetailModel {
    public boolean saveOrderDetail(InventoryOrderTm cartTmList, int supOrderId, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO suporderdetail (itemId,supOrderId,qty) VALUES(?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,cartTmList.getId());
        pstm.setInt(2,supOrderId);
        pstm.setInt(3,qty);
        return pstm.executeUpdate() > 0;
    }

    public boolean saveOrderDetails(List<InventoryOrderTm> cartTmList, int supOrderId, int qty) throws SQLException {
        for(InventoryOrderTm tm : cartTmList) {
            if(!saveOrderDetail(tm,supOrderId,qty)) {
                return false;
            }
        }
        return true;
    }
}
