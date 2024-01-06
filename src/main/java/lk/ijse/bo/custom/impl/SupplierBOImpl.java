package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.SupplierDto;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    private SupplierDAO supplierDAO= (SupplierDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.SUPPLIER);


    @Override
    public boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(dto.getId(),dto.getName(),dto.getContact(),dto.getAddress(),dto.getCategory()));
    }

    @Override
    public List<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException {
        List<Supplier> all = supplierDAO.getAll();
        ArrayList<SupplierDto> allSupplier = new ArrayList<SupplierDto>();
        for (Supplier supplier:all) {
            allSupplier.add(new SupplierDto(supplier.getId(),
                    supplier.getName(),
                    supplier.getContact(),
                    supplier.getAddress(),
                    supplier.getCategory()));
        }
        return allSupplier;
    }

    @Override
    public boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getId(),dto.getName(),dto.getContact(),dto.getAddress(),dto.getCategory()));
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
        Supplier search = supplierDAO.search(id);
        SupplierDto supplierDto = new SupplierDto(search.getId(),search.getName(),search.getContact(),search.getAddress(),search.getCategory());
        return supplierDto;
    }

    @Override
    public int generateNextSupId() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNextSupId();
    }

    @Override
    public List<SupplierDto> getItemSupplier(String code) throws SQLException, ClassNotFoundException {
        List<Supplier> itemSupplier = supplierDAO.getItemSupplier(code);
        ArrayList<SupplierDto> items = new ArrayList<SupplierDto>();
        for (Supplier supplier: itemSupplier) {
            items.add(new SupplierDto(supplier.getId(),supplier.getName()
            ,supplier.getContact(),
                    supplier.getAddress(),
                    supplier.getCategory()));
        }
        return items;
    }
}
