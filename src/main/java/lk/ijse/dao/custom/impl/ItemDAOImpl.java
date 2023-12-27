package lk.ijse.dao.custom.impl;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.InventoryOrderTm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public int generateNextOrderId() throws SQLException {
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

    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {

        Blob imgBlob = new javax.sql.rowset.serial.SerialBlob(itemDto.getImg());

        return SQLutil.execute("insert into item(itemId,description,qty,name,price,img,category) values (?,?,?,?,?,?,?)"
        ,itemDto.getItemId(),itemDto.getDescription(),itemDto.getQty(),itemDto.getName(),itemDto.getPrice(),imgBlob,itemDto.getCategory());
    }

    @Override
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

    @Override
    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        Blob imgBlob = new javax.sql.rowset.serial.SerialBlob(itemDto.getImg());

        return SQLutil.execute("UPDATE item SET description=?,qty=?,name=?,price=?,img=?,category=? WHERE itemId=?",
                itemDto.getDescription(),itemDto.getQty(),itemDto.getName(),itemDto.getPrice(),imgBlob,itemDto.getCategory(),itemDto.getItemId());

    }

    public boolean updateQty(int itemID,int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE item SET qty =? WHERE itemId=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setInt(1, qty);
        pstm.setInt(2, itemID);
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean updateItems(List<CartTm> cartTmList, int qty) throws SQLException {
        for(CartTm tm : cartTmList) {
            if(!updateQty(Integer.parseInt(tm.getItemId()), qty)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateInventoryOrderItem(List<InventoryOrderTm> cartTmList, int qty) throws SQLException {
        for(InventoryOrderTm tm : cartTmList) {
            if(!updateQty(Integer.parseInt(String.valueOf(tm.getId())), qty)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemDto searchItems(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("select * from item where name=?",id);

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

    @Override
    public List<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT * FROM item");

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

    @Override
    public boolean deleteItem(int focusedIndex) throws SQLException, ClassNotFoundException {

        return SQLutil.execute("DELETE FROM item WHERE itemId=?",focusedIndex);
    }

    @Override
    public String returnLbItemlValue() throws SQLException, ClassNotFoundException {
        String ItemCount;

        ResultSet resultSet = SQLutil.execute("SELECT COUNT(itemId) FROM item");
        while (resultSet.next()){
            ItemCount= String.valueOf(resultSet.getInt(1));
            return ItemCount;
        }
        return null;
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT itemId FROM item WHERE itemId=?",id);
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public ItemDto searchItemName(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("select * from item where name=?",id);

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

    @Override
    public List<ItemDto> getCategoryName(String category) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT * FROM item WHERE category =? ",category);

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
}
