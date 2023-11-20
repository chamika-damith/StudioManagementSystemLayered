package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO employee (empId,name,salary,email,type,address) VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,dto.getEmpId());
        pstm.setString(2,dto.getName());
        pstm.setDouble(3,dto.getSalary());
        pstm.setString(4,dto.getEmail());
        pstm.setString(5,dto.getType());
        pstm.setString(6,dto.getAddress());
        return pstm.executeUpdate() > 0;
    }

    public static int generateNextEmpId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT empId FROM employee ORDER BY empId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitEmpId(resultSet.getInt(1));
        }
        return splitEmpId(0);
    }

    private static int splitEmpId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE employee SET name = ? , salary=? , email=? , type=?,address =?  WHERE empId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setDouble(2,dto.getSalary());
        pstm.setString(3,dto.getEmail());
        pstm.setString(4,dto.getType());
        pstm.setString(5,dto.getAddress());
        pstm.setInt(6,dto.getEmpId());


        return pstm.executeUpdate() > 0;
    }

    public EmployeeDto searchEmployee(int empId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="select * from employee where empId = ?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, empId);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto =null;
        while (resultSet.next()) {
            int textId = resultSet.getInt(1);
            String textName = resultSet.getString(2);
            double textSalary = resultSet.getDouble(3);
            String textEmail = resultSet.getString(4);
            String textType = resultSet.getString(5);
            String address = resultSet.getString(6);

            dto = new EmployeeDto(textId,textName,textSalary,textEmail,textType,address);
        }
        return dto;
    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM employee";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<EmployeeDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new EmployeeDto(
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

    public boolean deleteEmployee(int focusedIndex) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM employee WHERE empId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1, focusedIndex);
        return pstm.executeUpdate() > 0;
    }

    public boolean isExists(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT empId FROM employee WHERE empId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    public static String returnLbEmployeeValue() throws SQLException {
        String empCount;
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(empId) FROM employee";

        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            empCount= String.valueOf(resultSet.getInt(1));
            return empCount;
        }
        return null;
    }
}
