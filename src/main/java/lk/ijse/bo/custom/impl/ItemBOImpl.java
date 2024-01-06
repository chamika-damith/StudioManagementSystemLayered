package lk.ijse.bo.custom.impl;

import javafx.scene.image.Image;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.InventoryOrderTm;
import lk.ijse.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
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
        return itemDAO.save(new Item(dto.getItemId(),dto.getDescription(),dto.getQty(),dto.getName(),dto.getPrice(),dto.getImg(),
                dto.getCategory()));
    }

    @Override
    public List<ItemDto> getAll() throws SQLException, ClassNotFoundException {
        List<Item> all = itemDAO.getAll();
        ArrayList<ItemDto> dtoList = new ArrayList<>();
        for (Item item: all) {
            dtoList.add(new ItemDto(item.getItemId(),
                    item.getDescription(),
                    item.getQty(),
                    item.getName(),
                    item.getPrice(),
                    item.getImg(),
                    item.getCategory()));
        }
        return dtoList;
    }

    @Override
    public boolean update(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItemId(),dto.getDescription(),dto.getQty(),dto.getName(),dto.getPrice(),dto.getImg(),
                dto.getCategory()));
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
        Item search = itemDAO.search(id);
        ItemDto itemDto = new ItemDto(search.getItemId(),search.getDescription(),search.getQty(),search.getName(),
                search.getPrice(),search.getImg(),search.getCategory());
        return itemDto;
    }

    @Override
    public ItemDto searchItemName(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.searchItemName(id);
        ItemDto itemDto = new ItemDto(item.getItemId(),item.getDescription(),item.getQty(),item.getName(),item.getPrice(),item.getImg(),item.getCategory());
        return itemDto;
    }

    @Override
    public List<ItemDto> getCategoryName(String category) throws SQLException, ClassNotFoundException {
        List<Item> categoryName = itemDAO.getCategoryName(category);
        ArrayList<ItemDto> categoryList = new ArrayList<>();
        for (Item item: categoryName) {
            categoryList.add(new ItemDto(item.getItemId(),item.getDescription(),item.getQty(),item.getName(),item.getPrice(),
                    item.getImg(),item.getCategory()));
        }
        return categoryList;
    }
}
