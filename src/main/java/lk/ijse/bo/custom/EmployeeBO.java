package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(int focusedIndex) throws SQLException, ClassNotFoundException;
    boolean isExistsEmployee(int id) throws SQLException, ClassNotFoundException;
    EmployeeDto searchEmployee(int id) throws SQLException, ClassNotFoundException;
    int generateNextEmpId() throws SQLException, ClassNotFoundException;
    String returnLbEmployeeValue() throws SQLException, ClassNotFoundException;

}
