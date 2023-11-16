package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.PackageDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageModel {
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

    public boolean savePackageDto(PackageDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO packages (packageId,name,price,type) VALUES (?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, dto.getPkgId());
        pstm.setString(2,dto.getName());
        pstm.setDouble(3,dto.getPrice());
        pstm.setString(4,dto.getType());
        return pstm.executeUpdate() > 0;
    }
}
