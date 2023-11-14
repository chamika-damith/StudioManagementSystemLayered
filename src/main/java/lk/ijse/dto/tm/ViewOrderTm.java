package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class ViewOrderTm {
    int orderId;
    String customerName;
    String address;
    String email;
    String mobile;
    Button btnMore;
}
