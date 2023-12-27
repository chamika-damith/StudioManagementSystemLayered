package lk.ijse.dao;

import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    boolean save(T dto) throws SQLException;
    List<T> getAll() throws SQLException;
    boolean update(T dto) throws SQLException;
    boolean delete(int focusedIndex) throws SQLException;
    boolean isExists(int id) throws SQLException;
    CustomerDto search(int id) throws SQLException;
}
