package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class ItemTm {
    int itemId;
    String name;
    int qty;
    double price;
    String description;
    String category;
    Button btn;
}
