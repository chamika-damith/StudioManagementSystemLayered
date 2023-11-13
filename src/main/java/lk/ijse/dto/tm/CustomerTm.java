package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class CustomerTm {
    int cusId;
    String name;
    String mobile;
    String email;
    String address;
    Button btn;
}
