package lk.ijse.entity;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor

public class InventoryOrderDetail {
    int itemId;
    int supOrderId;
    String description;
    String name;
    double price;
    String category;
    int qty;
    Date orderDate;
}
