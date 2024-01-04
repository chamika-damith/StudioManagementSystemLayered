package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SupplierDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;
    List<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException;
    boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteSupplier(int focusedIndex) throws SQLException, ClassNotFoundException;
    boolean isExistsSupplier(int id) throws SQLException, ClassNotFoundException;
    SupplierDto searchSupplier(int id) throws SQLException, ClassNotFoundException;
    int generateNextSupId() throws SQLException, ClassNotFoundException;
    List<SupplierDto> getItemSupplier(String code) throws SQLException, ClassNotFoundException;
}
