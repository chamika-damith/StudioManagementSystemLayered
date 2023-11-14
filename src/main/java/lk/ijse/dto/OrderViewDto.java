package lk.ijse.dto;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

public class OrderViewDto {
    int orderId;
    String customerName;
    String address;
    String email;
    String mobile;

}
