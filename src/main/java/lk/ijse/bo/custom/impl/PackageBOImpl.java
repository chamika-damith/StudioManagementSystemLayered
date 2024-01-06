package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PackageBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PackageDAO;
import lk.ijse.dto.ServiceDto;
import lk.ijse.entity.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageBOImpl implements PackageBO {

    private PackageDAO packageDAO= (PackageDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.PACKAGE);

    @Override
    public boolean savePackage(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return packageDAO.save(new Service(dto.getPkgId(),dto.getName(),dto.getPrice(),dto.getType()));
    }

    @Override
    public List<ServiceDto> getAllPackage() throws SQLException, ClassNotFoundException {
        List<Service> all = packageDAO.getAll();
        ArrayList<ServiceDto> result = new ArrayList<>();
        for (Service service : all) {
            result.add(new ServiceDto(service.getPkgId(),
                    service.getName(),
                    service.getPrice(),
                    service.getType()));
        }
        return result;
    }

    @Override
    public boolean updatePackage(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return packageDAO.update(new Service(dto.getPkgId(),dto.getName(),dto.getPrice(),dto.getType()));
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
        Service search = packageDAO.search(id);
        ServiceDto serviceDto = new ServiceDto(search.getPkgId(), search.getName(), search.getPrice(), search.getType());
        return serviceDto;
    }

    @Override
    public int generateNextPkgId() throws SQLException, ClassNotFoundException {
        return packageDAO.generateNextPkgId();
    }
}
