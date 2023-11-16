package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO employee (empId,name,salary,email,type) VALUES (?,?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,dto.getEmpId());
        pstm.setString(2,dto.getName());
        pstm.setDouble(3,dto.getSalary());
        pstm.setString(4,dto.getEmail());
        pstm.setString(5,dto.getType());
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
}
