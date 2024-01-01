package lk.ijse.dao.custom;

import java.sql.SQLException;

public interface LoginDAO {
    boolean checkLogin(String userName, String Password) throws SQLException, ClassNotFoundException;
    int getUserId(String text) throws SQLException, ClassNotFoundException;
}
