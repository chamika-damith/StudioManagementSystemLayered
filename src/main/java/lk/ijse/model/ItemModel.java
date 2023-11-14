package lk.ijse.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.ItemDto;

import java.io.IOException;
import java.sql.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ItemModel {


    public static int generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT itemId FROM item ORDER BY itemId DESC LIMIT 1";
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

    public boolean saveItem(ItemDto itemDto) throws SQLException {

        Blob imgBlob = new javax.sql.rowset.serial.SerialBlob(itemDto.getImg());

        Connection connection = DbConnection.getInstance().getConnection();
        String sql="insert into item(itemId,description,qty,name,price,img,category) values (?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, itemDto.getItemId());
        pstm.setString(2, itemDto.getDescription());
        pstm.setInt(3, itemDto.getQty());
        pstm.setString(4, itemDto.getName());
        pstm.setDouble(5, itemDto.getPrice());
        pstm.setBlob(6, imgBlob);
        pstm.setString(7, itemDto.getCategory());

        return pstm.executeUpdate() > 0;
    }

    public byte[] imagenToByte(Image imgId) {
        BufferedImage bufferimage = SwingFXUtils.fromFXImage(imgId, null);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferimage, "jpg", output );
            ImageIO.write(bufferimage, "png", output );
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte [] data = output.toByteArray();
        return data;
    }

    public boolean updateItem(ItemDto itemDto) throws SQLException {
        Blob imgBlob = new javax.sql.rowset.serial.SerialBlob(itemDto.getImg());

        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE item SET description=?,qty=?,name=?,price=?,img=?,category=? WHERE itemId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1, itemDto.getDescription());
        pstm.setInt(2, itemDto.getQty());
        pstm.setString(3, itemDto.getName());
        pstm.setDouble(4, itemDto.getPrice());
        pstm.setBlob(5, imgBlob);
        pstm.setString(6, itemDto.getCategory());
        pstm.setInt(7, itemDto.getItemId());

        return pstm.executeUpdate() > 0;

    }

    public boolean updateQty(int id,int saveQty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE item SET qty =? WHERE itemId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, saveQty);
        pstm.setInt(2, id);
        return pstm.executeUpdate() > 0;
    }

    public ItemDto searchItems(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="select * from item where itemId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();

        ItemDto dto=null;
        if (resultSet.next()){
            int text=resultSet.getInt("itemId");
            String description = resultSet.getString("description");
            int qty = resultSet.getInt("qty");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            byte[] img = resultSet.getBytes("img");
            String category = resultSet.getString("category");

            dto=new ItemDto(text,description,qty,name,price,img,category);

        }

        return dto;

    }

    public List<ItemDto> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM item";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<ItemDto> dto=new ArrayList<>();

        while (resultSet.next()) {
                dto.add(new ItemDto(
                        resultSet.getInt("itemId"),
                        resultSet.getString("description"),
                        resultSet.getInt("qty"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getBytes("img"),
                        resultSet.getString("category")
                ));
        }
        return dto;
    }

    public boolean deleteItem(int focusedIndex) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM item WHERE itemId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1, focusedIndex);
        return pstm.executeUpdate() > 0;
    }

    public static String returnLbItemlValue() throws SQLException {
        String ItemCount;
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(itemId) FROM item";

        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            ItemCount= String.valueOf(resultSet.getInt(1));
            return ItemCount;
        }
        return null;
    }

    public boolean isExists(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT itemId FROM item WHERE itemId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setInt(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            return true;
        }
        return false;
    }
}
