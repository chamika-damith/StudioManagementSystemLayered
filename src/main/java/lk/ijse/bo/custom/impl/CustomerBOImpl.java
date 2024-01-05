package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
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
        return customerDAO.save(new Customer(dto.getCusId(),dto.getName(),dto.getMobile(),dto.getEmail(),dto.getAddress()));
    }

    @Override
    public List<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        List<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDto> dto=new ArrayList<CustomerDto>();
        for (Customer customer:all) {
            dto.add(new CustomerDto(customer.getCusId(),
                    customer.getName(),
                    customer.getMobile(),
                    customer.getEmail(),
                    customer.getAddress())
            );
        }
        return dto;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCusId(),dto.getName(),dto.getMobile(),dto.getEmail(),dto.getAddress()));
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
        Customer search = customerDAO.search(id);
        CustomerDto dto = new CustomerDto(search.getCusId(),search.getName(),search.getMobile(),search.getEmail(),search.getAddress());
        return dto;
    }
}
