package lk.ijse.bo;

import lk.ijse.bo.custom.impl.CustomerBOImpl;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER;
    }

    public SuperBO getBO(BOTypes boFactory) {
        switch (boFactory) {
            case CUSTOMER:
                return new CustomerBOImpl();
            default:
                return null;
        }
    }
}
