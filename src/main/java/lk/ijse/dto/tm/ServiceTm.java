package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@AllArgsConstructor
@Setter

public class ServiceTm {
    int id;
    String name;
    double price;
    String type;
    Button btn;
}
