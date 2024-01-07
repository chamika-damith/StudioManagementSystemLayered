package lk.ijse.dao.custom.impl;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.dto.BookingReportDto;
import lk.ijse.dto.ViewBookingDto;

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

}
