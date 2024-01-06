package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class Item {
    int itemId;
    String description;
    int qty;
    String name;
    double price;
    byte[] img;
    String category;
}
