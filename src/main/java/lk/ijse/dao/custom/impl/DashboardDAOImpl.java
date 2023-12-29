package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.DashboardDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.DasboardDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAOImpl implements DashboardDAO {

    @Override
    public List<DasboardDto> getChartData() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("select monthname(o.orderDate) as monthName,sum(o.totprice) as totalPrice from orders o GROUP BY MONTH(o.orderDate), MONTHNAME(o.orderDate)");
        ArrayList<DasboardDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new DasboardDto(
                    resultSet.getString("monthName"),
                    resultSet.getInt("totalPrice")
            ));
        }
        return dtoList;
    }

    @Override
    public boolean saveUser(String userName, String password) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO user(username, password) VALUES (?,?)",userName,password);
    }

    @Override
    public boolean updateUser(String username,String newPassword) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE user SET password=? where username=?",newPassword,username);
    }

    @Override
    public boolean deleteUser(String username,String password) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM user WHERE username=? AND password=?",username,password);
    }
}
