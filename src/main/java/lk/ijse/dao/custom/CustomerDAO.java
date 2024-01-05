package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    int generateNextCusId() throws SQLException, ClassNotFoundException;
    String returnLbCuslValue() throws SQLException, ClassNotFoundException;
}
