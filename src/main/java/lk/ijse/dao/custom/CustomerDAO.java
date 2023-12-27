package lk.ijse.dao.custom;

import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    int generateNextCusId() throws SQLException;
    boolean saveCustomer(CustomerDto dto) throws SQLException;
    List<CustomerDto> getAllCustomer() throws SQLException;
    boolean updateCustomer(CustomerDto dto) throws SQLException;
    CustomerDto searchCustomer(int id) throws SQLException;
    boolean deleteCustomer(int focusedIndex) throws SQLException;
    String returnLbCuslValue() throws SQLException;
    boolean isExists(int id) throws SQLException;
}
