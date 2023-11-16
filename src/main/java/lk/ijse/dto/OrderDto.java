package lk.ijse.dto;

import lk.ijse.dto.tm.CartTm;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class OrderDto {
    int orderId;
    Date orderDate;
    Date returnDate;
    int userId;
    int cusId;
    double total;
    int qty;
    int buyItemQty;
    List<CartTm> cartTmList=new ArrayList<>();

}
