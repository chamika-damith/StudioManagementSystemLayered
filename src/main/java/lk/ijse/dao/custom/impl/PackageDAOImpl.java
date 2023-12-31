package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.PackageDAO;
import lk.ijse.dto.ServiceDto;

import java.sql.SQLException;
import java.util.List;

public class PackageDAOImpl implements PackageDAO {
    @Override
    public boolean save(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO packages (packageId,name,price,type) VALUES (?,?,?,?)", dto.getPkgId(),dto.getName(),dto.getPrice(),dto.getType());
    }

    @Override
    public List<ServiceDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ServiceDto search(int id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
