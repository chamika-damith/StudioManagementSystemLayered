package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class InventoryOrderTm {
    int id;
    String description;
    Date orderDate;
    double price;
    int qty;
    Button btn;
}
