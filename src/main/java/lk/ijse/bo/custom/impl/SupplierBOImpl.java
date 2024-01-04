package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.SupplierDto;

import java.sql.SQLException;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    private SupplierDAO supplierDAO= (SupplierDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.SUPPLIER);


    @Override
    public boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(dto);
    }

    @Override
    public List<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAll();
    }

    @Override
    public boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(dto);
    }

    @Override
    public boolean deleteSupplier(int focusedIndex) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(focusedIndex);
    }

    @Override
    public boolean isExistsSupplier(int id) throws SQLException, ClassNotFoundException {
        return supplierDAO.isExists(id);
    }

    @Override
    public SupplierDto searchSupplier(int id) throws SQLException, ClassNotFoundException {
        return supplierDAO.search(id);
    }

    @Override
    public int generateNextSupId() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNextSupId();
    }

    @Override
    public List<SupplierDto> getItemSupplier(String code) throws SQLException, ClassNotFoundException {
        return supplierDAO.getItemSupplier(code);
    }
}
