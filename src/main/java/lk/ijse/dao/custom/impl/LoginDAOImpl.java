package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public boolean checkLogin(String userName, String Password) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT * FROM user WHERE userName=? AND password=? ",userName,Password);

        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public int getUserId(String text) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT userId FROM user WHERE username=?",text);
        if (resultSet.next()) {
            int userId = resultSet.getInt("userId");
            return userId;
        }else {
            return 0;
        }
    }
}
