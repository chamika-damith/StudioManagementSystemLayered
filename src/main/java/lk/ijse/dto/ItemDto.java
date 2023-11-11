package lk.ijse.dto;

import javafx.scene.image.Image;
import lombok.*;

import java.sql.Blob;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class ItemDto {

    int itemId;
    String description;
    int qty;
    String name;
    double price;
    byte[] img;
    String category;

}
