package lk.ijse.bo.custom;

import javafx.scene.image.Image;
import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.InventoryOrderTm;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    int generateNextOrderId() throws SQLException;
    byte[] imagenToByte(Image imgId);
    boolean updateItems(List<CartTm> cartTmList, int qty) throws SQLException;
    boolean updateInventoryOrderItem(List<InventoryOrderTm> cartTmList, int qty) throws SQLException;
    String returnLbItemlValue() throws SQLException, ClassNotFoundException;
    boolean save(ItemDto dto) throws SQLException, ClassNotFoundException;
    List<ItemDto> getAll() throws SQLException, ClassNotFoundException;

    boolean update(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    ItemDto search(int id) throws SQLException, ClassNotFoundException;
    ItemDto searchItemName(String id) throws SQLException, ClassNotFoundException;
    List<ItemDto> getCategoryName(String category) throws SQLException, ClassNotFoundException;
}
