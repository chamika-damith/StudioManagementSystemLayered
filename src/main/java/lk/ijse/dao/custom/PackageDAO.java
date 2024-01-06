package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Service;

import java.sql.SQLException;

public interface PackageDAO extends CrudDAO<Service> {
    int generateNextPkgId() throws SQLException, ClassNotFoundException;
}
