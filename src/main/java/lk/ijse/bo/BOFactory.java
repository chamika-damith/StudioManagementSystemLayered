package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,BOOKING,DASHBOARD,EMPLOYEE,INVENTORY,INVENTORYORDER;
    }

    public SuperBO getBO(BOTypes boFactory) {
        switch (boFactory) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case BOOKING:
                return new BookingBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case INVENTORY:
                return new InventoryOrderBOImpl();
            case INVENTORYORDER:
                return new InventoryOrderDetailBOImpl();
            default:
                return null;
        }
    }
}
