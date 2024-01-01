package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.SupplierDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<SupplierDto> {
    int generateNextSupId() throws SQLException, ClassNotFoundException;
    List<SupplierDto> getItemSupplier(String code) throws SQLException, ClassNotFoundException;
}
