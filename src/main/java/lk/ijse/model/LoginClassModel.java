package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginClassModel {

    public boolean checkLogin(String userName, String Password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM user WHERE userName=? AND password=? ";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1,userName);
        pstm.setString(2, Password);

//        try {
//            String hashedPassword = hashPassword(Password);
//            System.out.println(hashedPassword);
//            pstm.setString(2, hashedPassword);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return false;
//        }

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return toHexString(hash);
    }

    private String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

}
