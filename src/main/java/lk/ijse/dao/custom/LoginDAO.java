package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.sql.SQLException;

public interface LoginDAO extends SuperDAO {
    boolean checkLogin(String userName, String Password) throws SQLException, ClassNotFoundException;
    int getUserId(String text) throws SQLException, ClassNotFoundException;
}
