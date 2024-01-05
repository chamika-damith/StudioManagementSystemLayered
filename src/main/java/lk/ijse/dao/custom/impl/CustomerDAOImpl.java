package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public int generateNextCusId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet=SQLutil.execute("SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1");
        if(resultSet.next()) {
            return splitCusId(resultSet.getInt(1));
        }
        return splitCusId(0);
    }

    private static int splitCusId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    @Override
    public String returnLbCuslValue() throws SQLException, ClassNotFoundException {
        String cusCount;
        ResultSet resultSet = SQLutil.execute("SELECT COUNT(cusId) FROM customer");
        while (resultSet.next()){
            cusCount= String.valueOf(resultSet.getInt(1));
            return cusCount;
        }
        return null;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {

        return SQLutil.execute("INSERT INTO customer(cusId,name,mobile,email,address) VALUES(?,?,?,?,?)",entity.getCusId(),entity.getName(),
                entity.getMobile(),entity.getEmail(),entity.getAddress());
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT * FROM customer");
        ArrayList<Customer> dtoList=new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(new Customer(
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
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("UPDATE customer SET name = ? , mobile = ? , email=? , address=? WHERE cusId=?",entity.getCusId(),entity.getName(),
                entity.getMobile(),entity.getEmail(),entity.getAddress());
    }

    @Override
    public boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException {
        return SQLutil.execute("DELETE FROM customer WHERE cusId=?",focusedIndex);
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT cusId FROM customer WHERE cusId=?",id);
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public Customer search(int id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("select * from customer where cusId = ?",id);

        Customer dto =null;
        while (resultSet.next()) {
            int textId = resultSet.getInt(1);
            String textName = resultSet.getString(2);
            String textmobile = resultSet.getString(3);
            String textEmail = resultSet.getString(4);
            String textAddress = resultSet.getString(5);

            dto = new Customer(textId,textName,textmobile,textEmail,textAddress);
        }
        return dto;
    }
}
