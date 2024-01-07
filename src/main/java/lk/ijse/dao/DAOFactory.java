package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    public static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getFactory(){
        return (daoFactory==null) ? daoFactory=new DAOFactory(): daoFactory;
    }

    public enum DADTypes{
        CUSTOMER,ITEM,ORDER,ORDERDETAIL,BOOKING,INVENTORY,INVENTORYDETAIL,EMPLOYEE,LOGIN,PACKAGE,SUPPLIER,DASHBOARD,QUERY;
    }
    public SuperDAO getDao(DADTypes daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            case BOOKING:
                return new BookingDAOImpl();
            case INVENTORY:
                return new InventoryOrderDAOImpl();
            case INVENTORYDETAIL:
                return new InventoryOrderDetailDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            case PACKAGE:
                return new PackageDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case DASHBOARD:
                return new DashboardDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
