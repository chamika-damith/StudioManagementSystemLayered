package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginClassModel {

    public boolean checkLogin(String userName, String Password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM user WHERE password=? AND userName=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1,Password);
        pstm.setString(2,userName);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return true;
        }
        return false;
    }
}
