package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.LoginBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.LoginDAO;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    private LoginDAO loginDAO= (LoginDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.LOGIN);

    @Override
    public boolean checkLogin(String userName, String Password) throws SQLException, ClassNotFoundException {
        return loginDAO.checkLogin(userName,Password);
    }

    @Override
    public int getUserId(String text) throws SQLException, ClassNotFoundException {
        return loginDAO.getUserId(text);
    }
}
