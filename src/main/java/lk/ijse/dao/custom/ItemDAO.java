package lk.ijse.dao.custom;

import javafx.scene.image.Image;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.InventoryOrderTm;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {
    int generateNextOrderId() throws SQLException;

    boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException;

    byte[] imagenToByte(Image imgId);

    boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException;

    boolean updateItems(List<CartTm> cartTmList, int qty) throws SQLException;
    boolean updateInventoryOrderItem(List<InventoryOrderTm> cartTmList, int qty) throws SQLException;
    ItemDto searchItems(String id) throws SQLException, ClassNotFoundException;
    List<ItemDto> getAllItems() throws SQLException, ClassNotFoundException;
    boolean deleteItem(int focusedIndex) throws SQLException, ClassNotFoundException;
    String returnLbItemlValue() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    ItemDto searchItemName(String id) throws SQLException, ClassNotFoundException;
    List<ItemDto> getCategoryName(String category) throws SQLException, ClassNotFoundException;
}
