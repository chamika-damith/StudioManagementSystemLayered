package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class OrderItemDetailFormDto {
    int orderId;
    int itemId;
    String description;
    String name;
    double price;
    String category;
    int qty;
    byte[] img;
}
