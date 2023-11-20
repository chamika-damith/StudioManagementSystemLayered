package lk.ijse.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class OrderItemDetailFormDto {
    int orderId;
    Date orderDate;
    int itemId;
    String description;
    String name;
    double totprice;
    double price;
    String category;
    int qty;
    byte[] img;
}
