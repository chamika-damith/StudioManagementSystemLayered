package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO employee (empId,name,salary,email,type,address) VALUES (?,?,?,?,?,?)",
                entity.getEmpId(),entity.getName(),entity.getSalary(),entity.getEmail(),entity.getType(),entity.getAddress());
    }

    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT * FROM employee");
        ArrayList<Employee> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new Employee(
                    resultSet.getInt("empId"),
                    resultSet.getString("name"),
                    resultSet.getDouble("salary"),
                    resultSet.getString("email"),
                    resultSet.getString("type"),
                    resultSet.getString("address")
            ));
        }
        return dtoList;
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE employee SET name = ? , salary=? , email=? , type=?,address =?  WHERE empId=?",
                entity.getName(),entity.getSalary(),entity.getEmail(),entity.getType(),entity.getAddress(),entity.getEmpId());
    }

    @Override
    public boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM employee WHERE empId=?",focusedIndex);
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT empId FROM employee WHERE empId=?",id);
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public Employee search(int id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("select * from employee where empId = ?",id);

        Employee dto =null;
        while (resultSet.next()) {
            int textId = resultSet.getInt(1);
            String textName = resultSet.getString(2);
            double textSalary = resultSet.getDouble(3);
            String textEmail = resultSet.getString(4);
            String textType = resultSet.getString(5);
            String address = resultSet.getString(6);

            dto = new Employee(textId,textName,textSalary,textEmail,textType,address);
        }
        return dto;
    }

    @Override
    public int generateNextEmpId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT empId FROM employee ORDER BY empId DESC LIMIT 1");
        if(resultSet.next()) {
            return splitEmpId(resultSet.getInt(1));
        }
        return splitEmpId(0);
    }

    private int splitEmpId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    @Override
    public String returnLbEmployeeValue() throws SQLException, ClassNotFoundException {
        String empCount;
        ResultSet resultSet = SQLutil.execute("SELECT COUNT(empId) FROM employee");
        while (resultSet.next()){
            empCount= String.valueOf(resultSet.getInt(1));
            return empCount;
        }
        return null;
    }
}
