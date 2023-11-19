package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.sql.*;

public class InventoryOrderModel {
    public static int generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT supOrderId FROM supplier_order ORDER BY supOrderId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getInt(1));
        }
        return splitOrderId(0);
    }

    private static int splitOrderId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

    public boolean isExists(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT supOrderId FROM supplier_order WHERE supOrderId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    public boolean saveOrder(int supOrderId, String description, Date orderDate,Date returnDate,String category,int supId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO supplier_order (supOrderId,description,orderDate,returnDate,category,supId) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1,supOrderId);
        pstm.setString(2,description);
        pstm.setDate(3,orderDate);
        pstm.setDate(4,returnDate);
        pstm.setString(5,category);
        pstm.setInt(6,supId);

        return pstm.executeUpdate() > 0;
    }

}
