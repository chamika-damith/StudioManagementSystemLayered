package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.DashboardBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.DashboardDAO;
import lk.ijse.dto.DasboardDto;

import java.sql.SQLException;
import java.util.List;

public class DashboardBOImpl implements DashboardBO {

    private DashboardDAO dashboardDAO= (DashboardDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.DASHBOARD);

    @Override
    public List<DasboardDto> getChartData() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getChartData();
    }

    @Override
    public boolean saveUser(String userName, String password) throws SQLException, ClassNotFoundException {
        return dashboardDAO.saveUser(userName,password);
    }

    @Override
    public boolean updateUser(String username, String newPassword) throws SQLException, ClassNotFoundException {
        return dashboardDAO.updateUser(username,newPassword);
    }

    @Override
    public boolean deleteUser(String username, String password) throws SQLException, ClassNotFoundException {
        return dashboardDAO.deleteUser(username,password);
    }
}
