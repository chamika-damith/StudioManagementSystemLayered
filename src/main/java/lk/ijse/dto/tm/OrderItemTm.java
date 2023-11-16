package lk.ijse.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class OrderItemTm {
    int itemId;
    String description;
    String name;
    double price;
    String category;
    int qty;
    byte[] img;
}
