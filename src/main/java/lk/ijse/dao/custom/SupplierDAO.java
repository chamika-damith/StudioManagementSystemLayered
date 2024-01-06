package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {
    int generateNextSupId() throws SQLException, ClassNotFoundException;
    List<Supplier> getItemSupplier(String code) throws SQLException, ClassNotFoundException;
}
