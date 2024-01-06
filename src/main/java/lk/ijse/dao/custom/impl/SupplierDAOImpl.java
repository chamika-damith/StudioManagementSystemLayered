package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public boolean save(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("INSERT INTO supplier(supId, name, contact, address, category) VALUES(?,?,?,?,?)",
                entity.getId(),entity.getName(),entity.getContact(),entity.getAddress(),entity.getCategory());
    }

    @Override
    public List<Supplier> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT * FROM supplier");
        ArrayList<Supplier> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new Supplier(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return dtoList;
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE supplier SET name = ? , contact = ? , address=? , category=? WHERE supId=?",
                entity.getName(),entity.getContact(),entity.getAddress(),entity.getCategory(),entity.getId());
    }

    @Override
    public boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM supplier WHERE supId=?",focusedIndex);
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT supId FROM supplier WHERE supId=?",id);
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public Supplier search(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLutil.execute("select * from supplier where supId = ?",id);

        Supplier dto =null;
        while (resultSet.next()) {
            int textId = resultSet.getInt(1);
            String textName = resultSet.getString(2);
            String textmobile = resultSet.getString(3);
            String textAddress = resultSet.getString(4);
            String textCategory = resultSet.getString(5);

            dto = new Supplier(textId,textName,textmobile,textAddress,textCategory);
        }
        return dto;
    }

    @Override
    public int generateNextSupId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT supId FROM supplier ORDER BY supId DESC LIMIT 1");
        if(resultSet.next()) {
            return splitSupId(resultSet.getInt(1));
        }
        return splitSupId(0);
    }

    private static int splitSupId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    @Override
    public List<Supplier> getItemSupplier(String code) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT * FROM supplier WHERE category=?",code);
        ArrayList<Supplier> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new Supplier(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return dtoList;
    }
}
