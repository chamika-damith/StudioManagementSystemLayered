package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {


    public static int generateNextCusId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitCusId(resultSet.getInt(1));
        }
        return splitCusId(0);
    }

    private static int splitCusId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO customer(cusId,name,mobile,email,address) VALUES(?,?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,dto.getCusId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getMobile());
        pstm.setString(4,dto.getEmail());
        pstm.setString(5,dto.getAddress());

        return pstm.executeUpdate() > 0;
    }

    public List<CustomerDto> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM customer";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<CustomerDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new CustomerDto(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return dtoList;

    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE customer SET name = ? , mobile = ? , email=? , address=? WHERE cusId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setString(2,dto.getMobile());
        pstm.setString(3,dto.getEmail());
        pstm.setString(4,dto.getAddress());
        pstm.setInt(5,dto.getCusId());

        return pstm.executeUpdate() > 0;
    }

    public CustomerDto searchCustomer(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="select * from customer where cusId = ?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, id);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto =null;
        while (resultSet.next()) {
            int textId = resultSet.getInt(1);
            String textName = resultSet.getString(2);
            String textmobile = resultSet.getString(3);
            String textEmail = resultSet.getString(4);
            String textAddress = resultSet.getString(5);

            dto = new CustomerDto(textId,textName,textmobile,textEmail,textAddress);
        }
        return dto;
    }
    public static String returnLbCuslValue() throws SQLException {
        String cusCount;
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(cusId) FROM customer";

        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            cusCount= String.valueOf(resultSet.getInt(1));
            return cusCount;
        }
        return null;
    }
}
