package lk.ijse.dao;

import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    boolean save(T dto) throws SQLException, ClassNotFoundException;
    List<T> getAll() throws SQLException, ClassNotFoundException;
    boolean update(T dto) throws SQLException, ClassNotFoundException;
    boolean delete(int focusedIndex) throws SQLException, ClassNotFoundException;
    boolean isExists(int id) throws SQLException, ClassNotFoundException;
    T search(int id) throws SQLException, ClassNotFoundException;
}
