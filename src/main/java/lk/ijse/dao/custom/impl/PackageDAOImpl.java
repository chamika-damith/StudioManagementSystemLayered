package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.PackageDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.ServiceDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageDAOImpl implements PackageDAO {
    @Override
    public boolean save(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO packages (packageId,name,price,type) VALUES (?,?,?,?)", dto.getPkgId(),dto.getName(),dto.getPrice(),dto.getType());
    }

    @Override
    public List<ServiceDto> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT * FROM packages");
        ArrayList<ServiceDto> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new ServiceDto(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean update(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE packages SET name = ? , price = ? , type=?  WHERE packageId=?",
                dto.getName(),dto.getPrice(),dto.getType(),dto.getPkgId());
    }

    @Override
    public boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM packages WHERE packageId=?",focusedIndex);
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT packageId FROM packages WHERE packageId=?",id);
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public ServiceDto search(int id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("select * from packages where packageId = ?",id);

        ServiceDto dto =null;
        while (resultSet.next()) {
            int textId = resultSet.getInt(1);
            String textName = resultSet.getString(2);
            double textPrice = resultSet.getDouble(3);
            String textType = resultSet.getString(4);

            dto = new ServiceDto(textId,textName,textPrice,textType);
        }
        return dto;
    }

    @Override
    public int generateNextPkgId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute( "SELECT packageId FROM packages ORDER BY packageId DESC LIMIT 1");
        if(resultSet.next()) {
            return splitPkgId(resultSet.getInt(1));
        }
        return splitPkgId(0);
    }

    private static int splitPkgId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }
}
