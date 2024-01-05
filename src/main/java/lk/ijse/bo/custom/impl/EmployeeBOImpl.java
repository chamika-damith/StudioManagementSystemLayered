package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    private EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(dto.getEmpId(),dto.getName(),dto.getSalary(),dto.getEmail(),dto.getType(),dto.getAddress()));
    }

    @Override
    public List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        List<Employee> all = employeeDAO.getAll();
        ArrayList<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
        for (Employee employee: all) {
            employeeList.add(new EmployeeDto(
                    employee.getEmpId(),
                    employee.getName(),
                    employee.getSalary(),
                    employee.getEmail(),
                    employee.getType(),
                    employee.getAddress()
            ));
        }
        return employeeList;
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getEmpId(),dto.getName(),dto.getSalary(),dto.getEmail(),dto.getType(),dto.getAddress()));
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
        Employee search = employeeDAO.search(id);
        EmployeeDto employeeDto = new EmployeeDto(search.getEmpId(), search.getName(), search.getSalary(), search.getEmail(), search.getType(), search.getAddress());
        return employeeDto;
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
