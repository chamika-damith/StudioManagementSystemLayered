package lk.ijse.bo.custom.impl;

import javafx.scene.image.Image;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.InventoryOrderTm;

import java.sql.SQLException;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    private ItemDAO itemDAO= (ItemDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.ITEM);

    @Override
    public int generateNextOrderId() throws SQLException {
        return itemDAO.generateNextOrderId();
    }

    @Override
    public byte[] imagenToByte(Image imgId) {
        return itemDAO.imagenToByte(imgId);
    }

    @Override
    public boolean updateItems(List<CartTm> cartTmList, int qty) throws SQLException {
        return itemDAO.updateItems(cartTmList,qty);
    }

    @Override
    public boolean updateInventoryOrderItem(List<InventoryOrderTm> cartTmList, int qty) throws SQLException {
        return itemDAO.updateInventoryOrderItem(cartTmList,qty);
    }

    @Override
    public String returnLbItemlValue() throws SQLException, ClassNotFoundException {
        return itemDAO.returnLbItemlValue();
    }

    @Override
    public boolean save(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(dto);
    }

    @Override
    public List<ItemDto> getAll() throws SQLException, ClassNotFoundException {
        return itemDAO.getAll();
    }

    @Override
    public boolean update(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(dto);
    }

    @Override
    public boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(focusedIndex);
    }

    @Override
    public boolean isExists(int id) throws SQLException, ClassNotFoundException {
        return itemDAO.isExists(id);
    }

    @Override
    public ItemDto search(int id) throws SQLException, ClassNotFoundException {
        return itemDAO.search(id);
    }

    @Override
    public ItemDto searchItemName(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.searchItemName(id);
    }

    @Override
    public List<ItemDto> getCategoryName(String category) throws SQLException, ClassNotFoundException {
        return itemDAO.getCategoryName(category);
    }
}
