package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.ServiceDto;

import java.sql.SQLException;

public interface PackageDAO extends CrudDAO<ServiceDto> {
    int generateNextPkgId() throws SQLException, ClassNotFoundException;
}
