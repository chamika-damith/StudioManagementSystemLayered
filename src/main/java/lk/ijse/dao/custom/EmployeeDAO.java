package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {
    int generateNextEmpId() throws SQLException, ClassNotFoundException;
    String returnLbEmployeeValue() throws SQLException, ClassNotFoundException;
}
