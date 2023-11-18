package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public boolean isExists(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT supId FROM supplier WHERE supId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO supplier(supId, name, contact, address, category) VALUES(?,?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getContact());
        pstm.setString(4,dto.getAddress());
        pstm.setString(5,dto.getCategory());

        return pstm.executeUpdate() > 0;
    }

    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE supplier SET name = ? , contact = ? , address=? , category=? WHERE supId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setString(2,dto.getContact());
        pstm.setString(3,dto.getAddress());
        pstm.setString(4,dto.getCategory());
        pstm.setInt(5,dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public List<SupplierDto> getAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM supplier";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<SupplierDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new SupplierDto(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return dtoList;
    }

    public boolean deleteSupplier(int focusedIndex) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM supplier WHERE supId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1, focusedIndex);
        return pstm.executeUpdate() > 0;
    }

    public SupplierDto searchSupplier(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="select * from supplier where supId = ?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, id);

        ResultSet resultSet = pstm.executeQuery();

        SupplierDto dto =null;
        while (resultSet.next()) {
            int textId = resultSet.getInt(1);
            String textName = resultSet.getString(2);
            String textmobile = resultSet.getString(3);
            String textAddress = resultSet.getString(4);
            String textCategory = resultSet.getString(5);

            dto = new SupplierDto(textId,textName,textmobile,textAddress,textCategory);
        }
        return dto;
    }

    public static int generateNextSupId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT supId FROM supplier ORDER BY supId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitSupId(resultSet.getInt(1));
        }
        return splitSupId(0);
    }

    private static int splitSupId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    public List<SupplierDto> getItemSupplier(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM supplier WHERE category=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<SupplierDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new SupplierDto(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return dtoList;
    }
}
