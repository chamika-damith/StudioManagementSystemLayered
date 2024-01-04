package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    boolean checkLogin(String userName, String Password) throws SQLException, ClassNotFoundException;
    int getUserId(String text) throws SQLException, ClassNotFoundException;
}
