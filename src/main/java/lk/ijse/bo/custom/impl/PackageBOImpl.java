package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PackageBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PackageDAO;
import lk.ijse.dto.ServiceDto;

import java.sql.SQLException;
import java.util.List;

public class PackageBOImpl implements PackageBO {

    private PackageDAO packageDAO= (PackageDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.PACKAGE);

    @Override
    public boolean savePackage(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return packageDAO.save(dto);
    }

    @Override
    public List<ServiceDto> getAllPackage() throws SQLException, ClassNotFoundException {
        return packageDAO.getAll();
    }

    @Override
    public boolean updatePackage(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return packageDAO.update(dto);
    }

    @Override
    public boolean deletePackage(int focusedIndex) throws SQLException, ClassNotFoundException {
        return packageDAO.delete(focusedIndex);
    }

    @Override
    public boolean isExistsPackage(int id) throws SQLException, ClassNotFoundException {
        return packageDAO.isExists(id);
    }

    @Override
    public ServiceDto searchPackage(int id) throws SQLException, ClassNotFoundException {
        return packageDAO.search(id);
    }

    @Override
    public int generateNextPkgId() throws SQLException, ClassNotFoundException {
        return packageDAO.generateNextPkgId();
    }
}
