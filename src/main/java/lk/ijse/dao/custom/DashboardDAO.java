package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.dto.DasboardDto;

import java.sql.SQLException;
import java.util.List;

public interface DashboardDAO extends SuperDAO {
    List<DasboardDto> getChartData() throws SQLException, ClassNotFoundException;
    boolean saveUser(String userName, String password) throws SQLException, ClassNotFoundException;
    boolean updateUser(String username,String newPassword) throws SQLException, ClassNotFoundException;
    boolean deleteUser(String username,String password) throws SQLException, ClassNotFoundException;
}
