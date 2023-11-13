package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class CartTm {
    String itemId;
    String description;
    Date orderDate;
    double price;
    int qty;
    Button btn;
}
