package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    private EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(dto);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAll();
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(dto);
    }

    @Override
    public boolean deleteEmployee(int focusedIndex) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(focusedIndex);
    }

    @Override
    public boolean isExistsEmployee(int id) throws SQLException, ClassNotFoundException {
        return employeeDAO.isExists(id);
    }

    @Override
    public EmployeeDto searchEmployee(int id) throws SQLException, ClassNotFoundException {
        return employeeDAO.search(id);
    }

    @Override
    public int generateNextEmpId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNextEmpId();
    }

    @Override
    public String returnLbEmployeeValue() throws SQLException, ClassNotFoundException {
        return employeeDAO.returnLbEmployeeValue();
    }
}
