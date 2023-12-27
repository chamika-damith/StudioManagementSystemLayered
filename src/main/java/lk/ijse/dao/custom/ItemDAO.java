package lk.ijse.dao.custom;

import javafx.scene.image.Image;
import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.InventoryOrderTm;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<ItemDto> {
    int generateNextOrderId() throws SQLException;
    byte[] imagenToByte(Image imgId);
    boolean updateItems(List<CartTm> cartTmList, int qty) throws SQLException;
    boolean updateInventoryOrderItem(List<InventoryOrderTm> cartTmList, int qty) throws SQLException;
    String returnLbItemlValue() throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    ItemDto searchItemName(String id) throws SQLException, ClassNotFoundException;
    List<ItemDto> getCategoryName(String category) throws SQLException, ClassNotFoundException;
}
