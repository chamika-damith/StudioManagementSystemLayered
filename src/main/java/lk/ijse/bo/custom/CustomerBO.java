package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    int generateNextCusId() throws SQLException, ClassNotFoundException;
    String returnLbCuslValue() throws SQLException, ClassNotFoundException;
    boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
    List<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(int focusedIndex) throws SQLException, ClassNotFoundException;
    boolean isExistsCustomer(int id) throws SQLException, ClassNotFoundException;
    CustomerDto searchCustomer(int id) throws SQLException, ClassNotFoundException;

}
