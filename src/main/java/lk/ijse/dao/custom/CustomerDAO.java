package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<CustomerDto> {
    int generateNextCusId() throws SQLException;
    String returnLbCuslValue() throws SQLException;
}
