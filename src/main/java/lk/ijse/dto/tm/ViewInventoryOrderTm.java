package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class ViewInventoryOrderTm {
    int id;
    String supName;
    String address;
    String category;
    String mobile;
    Button btn;
}
