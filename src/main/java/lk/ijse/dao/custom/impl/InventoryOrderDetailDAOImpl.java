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
        ResultSet resultSet = SQLutil.execute("SELECT b.bookingId,p.price,SUM(p.price) OVER () AS total FROM booking b JOIN packages p on b.packageId = p.packageId WHERE b.bookingId=? GROUP BY b.bookingId",id);
        int total = 0;

        if (resultSet.next()){
            total = resultSet.getInt("total");
        }

        return total;
    }

    @Override
    public List<InventoryOrderViewDto> getAllItemsOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT o.supOrderId,s.name,s.address,s.category,s.contact FROM supplier_order o JOIN supplier s ON o.supId = s.supId");

        ArrayList<InventoryOrderViewDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new InventoryOrderViewDto(
                    resultSet.getInt("supOrderId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("category"),
                    resultSet.getString("contact")
            ));
        }
        return dto;
    }
}
