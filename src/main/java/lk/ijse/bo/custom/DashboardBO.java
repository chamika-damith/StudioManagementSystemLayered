package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.DasboardDto;

import java.sql.SQLException;
import java.util.List;

public interface DashboardBO extends SuperBO {
    List<DasboardDto> getChartData() throws SQLException, ClassNotFoundException;
    boolean saveUser(String userName, String password) throws SQLException, ClassNotFoundException;
    boolean updateUser(String username,String newPassword) throws SQLException, ClassNotFoundException;
    boolean deleteUser(String username,String password) throws SQLException, ClassNotFoundException;
}
