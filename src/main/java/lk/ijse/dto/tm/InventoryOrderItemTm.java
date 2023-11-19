package lk.ijse.dto.tm;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor

public class InventoryOrderItemTm {
    int itemId;
    String description;
    String name;
    double price;
    String category;
    int qty;
}
