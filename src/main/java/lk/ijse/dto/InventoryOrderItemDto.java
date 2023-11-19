package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor

public class InventoryOrderItemDto {
    int itemId;
    String description;
    String name;
    double price;
    String category;
    int qty;
}
