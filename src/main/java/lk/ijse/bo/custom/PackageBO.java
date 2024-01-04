package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ServiceDto;

import java.sql.SQLException;
import java.util.List;

public interface PackageBO extends SuperBO {
    boolean savePackage(ServiceDto dto) throws SQLException, ClassNotFoundException;
    List<ServiceDto> getAllPackage() throws SQLException, ClassNotFoundException;
    boolean updatePackage(ServiceDto dto) throws SQLException, ClassNotFoundException;
    boolean deletePackage(int focusedIndex) throws SQLException, ClassNotFoundException;
    boolean isExistsPackage(int id) throws SQLException, ClassNotFoundException;
    ServiceDto searchPackage(int id) throws SQLException, ClassNotFoundException;
    int generateNextPkgId() throws SQLException, ClassNotFoundException;

}
