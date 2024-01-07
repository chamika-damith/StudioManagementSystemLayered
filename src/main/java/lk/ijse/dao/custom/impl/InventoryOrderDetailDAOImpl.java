package lk.ijse.dao.custom.impl;

import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.InventoryOrderDetailDAO;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.dto.InventoryOrderViewDto;
import lk.ijse.dto.tm.InventoryOrderTm;
import lk.ijse.entity.InventoryOrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryOrderDetailDAOImpl implements InventoryOrderDetailDAO {

    private QueryDAO queryDAO= (QueryDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.QUERY);

    public boolean saveOrderDetail(InventoryOrderTm cartTmList, int supOrderId, int qty) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO suporderdetail (itemId,supOrderId,qty) VALUES(?,?,?)",cartTmList.getId(),supOrderId,qty);
    }

    @Override
    public boolean saveOrderDetails(List<InventoryOrderTm> cartTmList, int supOrderId, int qty) throws SQLException, ClassNotFoundException {
        for(InventoryOrderTm tm : cartTmList) {
            if(!saveOrderDetail(tm,supOrderId,qty)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<InventoryOrderDetail> getAllValues(int id) throws SQLException, ClassNotFoundException {
        return queryDAO.getAllValues(id);
    }

    @Override
    public int getAllTotal(int id) throws SQLException, ClassNotFoundException {
        return queryDAO.getAllTotal(id);
    }

    @Override
    public List<InventoryOrderViewDto> getAllItemsOrder() throws SQLException, ClassNotFoundException {
        return queryDAO.getAllItemsOrder();
    }
}
