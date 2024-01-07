package lk.ijse.dao.custom.impl;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.dto.BookingReportDto;
import lk.ijse.dto.InventoryOrderViewDto;
import lk.ijse.dto.OrderViewDto;
import lk.ijse.dto.ViewBookingDto;
import lk.ijse.entity.InventoryOrderDetail;
import lk.ijse.entity.OrderDetail;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    //join query

    @Override
    public BookingReportDto getReportDetail(int id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT b.bookingId,p.name,p.price,SUM(p.price) OVER () AS total FROM booking b JOIN packages p on b.packageId = p.packageId WHERE b.bookingId=? GROUP BY b.bookingId",id);

        BookingReportDto dto =null;
        while (resultSet.next()) {
            int bookingId = resultSet.getInt("bookingId");
            String name = resultSet.getString("name");
            double price=resultSet.getDouble("price");
            int total=resultSet.getInt("total");


            dto = new BookingReportDto(bookingId,name,price,total);
        }
        System.out.println(dto.getBookingId());
        return dto;
    }

    @Override
    public List<ViewBookingDto> getTodayBooking(Date date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT b.bookingId,b.status,c.name,b.location,c.email,c.mobile FROM booking b JOIN customer c ON b.custId = c.cusId WHERE date=?",date);

        ArrayList<ViewBookingDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new ViewBookingDto(
                    resultSet.getInt("bookingId"),
                    resultSet.getString("name"),
                    resultSet.getString("location"),
                    resultSet.getString("email"),
                    resultSet.getString("mobile"),
                    resultSet.getBoolean("status")
            ));
        }
        return dto;
    }

    @Override
    public List<ViewBookingDto> getAllBooking() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLutil.execute("SELECT b.bookingId,b.status,c.name,b.location,c.email,c.mobile FROM booking b JOIN customer c ON b.custId = c.cusId");

        ArrayList<ViewBookingDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new ViewBookingDto(
                    resultSet.getInt("bookingId"),
                    resultSet.getString("name"),
                    resultSet.getString("location"),
                    resultSet.getString("email"),
                    resultSet.getString("mobile"),
                    resultSet.getBoolean("status")
            ));
        }
        return dto;
    }

    @Override
    public List<InventoryOrderDetail> getAllValues(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT so.supOrderId,so.orderDate,sod.itemId,sod.qty,so.description,so.category,item.name,item.price FROM supplier_order so JOIN suporderdetail sod on so.supOrderId = sod.supOrderId JOIN item item on sod.itemId = item.itemId WHERE sod.supOrderId=?",id);


        ArrayList<InventoryOrderDetail> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new InventoryOrderDetail(
                    resultSet.getInt("itemId"),
                    resultSet.getInt("supOrderId"),
                    resultSet.getString("description"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category"),
                    resultSet.getInt("qty"),
                    resultSet.getDate("orderDate")
            ));
        }
        return dtoList;
    }

    @Override
    public int getAllTotal(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT b.bookingId,p.price,SUM(p.price) OVER () AS total FROM booking b JOIN packages p on b.packageId = p.packageId WHERE b.bookingId=? GROUP BY b.bookingId",id);
        int total = 0;

        if (resultSet.next()){
            total = resultSet.getInt("total");
        }

        return total;
    }

    @Override
    public List<InventoryOrderViewDto> getAllItemsOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT o.supOrderId,s.name,s.address,s.category,s.contact FROM supplier_order o JOIN supplier s ON o.supId = s.supId");

        ArrayList<InventoryOrderViewDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new InventoryOrderViewDto(
                    resultSet.getInt("supOrderId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("category"),
                    resultSet.getString("contact")
            ));
        }
        return dto;
    }

    @Override
    public List<OrderViewDto> getAllItems() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLutil.execute("SELECT o.orderId,c.name,c.address,c.email,c.mobile FROM orders o JOIN customer c ON o.cusId = c.cusId");

        ArrayList<OrderViewDto> dto=new ArrayList<>();

        while (resultSet.next()) {
            dto.add(new OrderViewDto(
                    resultSet.getInt("orderId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("mobile")
            ));
        }
        return dto;
    }

    @Override
    public List<OrderDetail> getAllOrderDetailValues(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLutil.execute("SELECT orders.orderId,orders.orderDate,orders.totprice,item.itemId,od.qty,item.description,item.price,item.name,item.category,item.img FROM orders JOIN order_detail od on orders.orderId = od.orderId JOIN item item on od.itemId = item.itemId WHERE od.orderId=?",
                id);

        ArrayList<OrderDetail> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            System.out.println("resultset");
            dtoList.add(new OrderDetail(
                    resultSet.getInt("orderId"),
                    resultSet.getDate("orderDate"),
                    resultSet.getInt("itemId"),
                    resultSet.getString("description"),
                    resultSet.getString("name"),
                    resultSet.getDouble("totprice"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category"),
                    resultSet.getInt("qty"),
                    resultSet.getBytes("img")
            ));
        }
        return dtoList;
    }

}
