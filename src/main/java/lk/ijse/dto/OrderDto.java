package lk.ijse.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class OrderDto {
    int orderId;
    String description;
    Date orderDate;
    Date returnDate;
    int userId;
    int cusId;
    double total;
}
