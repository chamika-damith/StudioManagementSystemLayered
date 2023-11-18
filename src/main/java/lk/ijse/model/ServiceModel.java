package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ServiceDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceModel {
    public static int generateNextPkgId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT packageId FROM packages ORDER BY packageId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitPkgId(resultSet.getInt(1));
        }
        return splitPkgId(0);
    }

    private static int splitPkgId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    public boolean isExists(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT packageId FROM packages WHERE packageId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    public boolean savePackageDto(ServiceDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO packages (packageId,name,price,type) VALUES (?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, dto.getPkgId());
        pstm.setString(2,dto.getName());
        pstm.setDouble(3,dto.getPrice());
        pstm.setString(4,dto.getType());
        return pstm.executeUpdate() > 0;
    }

    public ServiceDto searchService(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="select * from packages where packageId = ?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, id);

        ResultSet resultSet = pstm.executeQuery();

        ServiceDto dto =null;
        while (resultSet.next()) {
            int textId = resultSet.getInt(1);
            String textName = resultSet.getString(2);
            double textPrice = resultSet.getDouble(3);
            String textType = resultSet.getString(4);

            dto = new ServiceDto(textId,textName,textPrice,textType);
        }
        return dto;
    }

    public List<ServiceDto> getAllService() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM packages";
        PreparedStatement pstm=connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<ServiceDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new ServiceDto(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            ));
        }
        return dtoList;
    }

    public boolean deleteService(int focusedIndex) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM packages WHERE packageId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1, focusedIndex);
        return pstm.executeUpdate() > 0;
    }

    public boolean updateService(ServiceDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE packages SET name = ? , price = ? , type=?  WHERE packageId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setDouble(2,dto.getPrice());
        pstm.setString(3,dto.getType());
        pstm.setInt(4,dto.getPkgId());

        return pstm.executeUpdate() > 0;
    }
}
