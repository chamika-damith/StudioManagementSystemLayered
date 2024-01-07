package lk.ijse.dao.custom.impl;
import lk.ijse.dao.SQLutil;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.dto.BookingReportDto;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}
