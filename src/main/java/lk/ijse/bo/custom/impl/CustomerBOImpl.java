package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getFactory().getDao(DAOFactory.DADTypes.CUSTOMER);

    @Override
    public int generateNextCusId() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNextCusId();
    }

    @Override
    public String returnLbCuslValue() throws SQLException, ClassNotFoundException {
        return customerDAO.returnLbCuslValue();
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(dto);
    }

    @Override
    public List<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(dto);
    }

    @Override
    public boolean deleteCustomer(int focusedIndex) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(focusedIndex);
    }

    @Override
    public boolean isExistsCustomer(int id) throws SQLException, ClassNotFoundException {
        return customerDAO.isExists(id);
    }

    @Override
    public CustomerDto searchCustomer(int id) throws SQLException, ClassNotFoundException {
        return customerDAO.search(id);
    }
}
