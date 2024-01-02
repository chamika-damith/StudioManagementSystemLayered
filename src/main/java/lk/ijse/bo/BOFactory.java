package lk.ijse.bo;

import lk.ijse.bo.custom.impl.BookingBOImpl;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bo.custom.impl.DashboardBOImpl;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,BOOKING,DASHBOARD;
    }

    public SuperBO getBO(BOTypes boFactory) {
        switch (boFactory) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case BOOKING:
                return new BookingBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            default:
                return null;
        }
    }
}
